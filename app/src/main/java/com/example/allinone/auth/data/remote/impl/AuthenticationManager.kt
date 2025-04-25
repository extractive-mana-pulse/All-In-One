package com.example.allinone.auth.data.remote.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.allinone.R
import com.example.allinone.auth.domain.model.UserCredentials
import com.example.allinone.auth.presentation.sealed.AuthResponse
import com.example.allinone.core.extension.toastMessage
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.ByteArrayOutputStream
import java.security.MessageDigest
import java.util.UUID

class AuthenticationManager(
    private val context: Context
) {

    private val auth = Firebase.auth
    private val storage = Firebase.storage.reference
    private val db = Firebase.firestore

    init {
        // This will keep the user signed in until explicitly signed out
        auth.addAuthStateListener { firebaseAuth ->
            // You can add custom logic here when auth state changes
        }
    }

    fun createAccountWithEmailAndPassword(
        email: String,
        password: String
    ) : Flow<AuthResponse> = callbackFlow {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(AuthResponse.Success)
                } else {
                    trySend(AuthResponse.Error(message = task.exception?.message ?: "Unknown error"))
                }
            }
        awaitClose()
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String
    ) : Flow<AuthResponse> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(AuthResponse.Success)
                } else {
                    trySend(AuthResponse.Error(message = it.exception?.message ?: "Unknown error"))
                }
            }
        awaitClose()
    }

    fun createNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

    fun getSignedInUser() : UserCredentials? = auth.currentUser?.run {
        UserCredentials(
            id = this.uid,
            email = this.email ?: "",
            displayName = this.displayName ?: "",
            imageUrl = this.photoUrl?.toString() ?: ""
        )
    }

    fun fetchUserData() : Flow<UserCredentials?> = callbackFlow {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    val basicUser = getSignedInUser()
                    if (document.exists() && basicUser != null) {
                        trySend(basicUser.copy())
                    } else {
                        trySend(basicUser)
                    }
                }
                .addOnFailureListener {
                    trySend(getSignedInUser())
                }
        } else {
            trySend(null)
        }

        awaitClose()
    }

    fun updateUserProfile(
        photoUri: Uri? = null
    ): Flow<AuthResponse> = callbackFlow {
        val user = auth.currentUser ?: run {
            trySend(AuthResponse.Error("No user is signed in"))
            close()
            return@callbackFlow
        }

        val profileUpdates = UserProfileChangeRequest.Builder().apply {
            photoUri?.let { setPhotoUri(it) }
        }.build()

        user.updateProfile(profileUpdates)
            .addOnSuccessListener {
                trySend(AuthResponse.Success)
                close()
            }
            .addOnFailureListener { exception ->
                trySend(AuthResponse.Error(exception.message ?: "Unknown error"))
                close()
            }
        awaitClose()
    }

    fun uploadProfilePicture(imageUri: Uri): Flow<AuthResponse> = callbackFlow {

        val user = auth.currentUser ?: run {
            trySend(AuthResponse.Error("No user is signed in"))
            close()
            return@callbackFlow
        }

        if (imageUri == Uri.EMPTY) {
            trySend(AuthResponse.Error("No image selected"))
            close()
            return@callbackFlow
        }

        try {
            val imageByteArray = convertImageUriToByteArray(imageUri)

            if (imageByteArray == null || imageByteArray.isEmpty()) {
                trySend(AuthResponse.Error("Failed to process image"))
                close()
                return@callbackFlow
            }

            val storageRef = FirebaseStorage.getInstance().reference
                .child("images/${user.uid}")

            val metadata = StorageMetadata.Builder()
                .setContentType("image/*")
                .build()

            val uploadTask = storageRef.putBytes(imageByteArray, metadata)

            uploadTask.addOnProgressListener { taskSnapshot ->
                val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toInt()
                trySend(AuthResponse.Loading(progress))
            }

            uploadTask.addOnFailureListener { exception ->
                trySend(AuthResponse.Error("Upload failed: ${exception.message}"))
                close()
            }

            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    val exception = task.exception ?: Exception("Unknown error during upload")
                    throw exception
                }
                storageRef.downloadUrl
            }.addOnSuccessListener { downloadUri ->
                user.updateProfile(
                    UserProfileChangeRequest.Builder()
                        .setPhotoUri(downloadUri)
                        .build()
                ).addOnSuccessListener {
                    trySend(AuthResponse.Success)
                    close()
                }.addOnFailureListener { exception ->
                    trySend(AuthResponse.Error("Failed to update profile: ${exception.message}"))
                    close()
                }
            }.addOnFailureListener { exception ->
                trySend(AuthResponse.Error("Failed to get download URL: ${exception.message}"))
                close()
            }
        } catch (e: Exception) {
            trySend(AuthResponse.Error("Upload process error: ${e.message}"))
            close()
        }
        awaitClose()
    }

    private fun convertImageUriToByteArray(imageUri: Uri): ByteArray? {
        return try {
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(imageUri) ?: return null

            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)

            outputStream.toByteArray()
        } catch (e: Exception) {
            null
        }
    }

    fun deleteImageFromFirebaseStorage(userId: String) {
        val imagePath = "profile_pictures/$userId.jpg"

        val storage = FirebaseStorage.getInstance()
        val imageRef = storage.reference.child(imagePath)

        imageRef.delete().addOnSuccessListener {
            toastMessage(
                context = context,
                message = "Image deleted successfully"
            )
        }.addOnFailureListener { exception ->
            toastMessage(
                context = context,
                message = "Failed to delete image: ${exception.message}"
            )
        }
    }

    // still in work, error: Developer console is not set up correctly
    fun signInWithGoogle() : Flow<AuthResponse> = callbackFlow {
        // Add debug logging
        Log.d("GoogleSignIn", "Starting Google sign-in process")

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.web_client_id))
            .setAutoSelectEnabled(false)
            .setNonce(createNonce())
            .build()

        // Log the web client ID to verify it's correct
        Log.d("GoogleSignIn", "Using web client ID: ${context.getString(R.string.web_client_id)}")

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        try {
            Log.d("GoogleSignIn", "Creating credential manager")
            val credentialManager = CredentialManager.create(context)

            Log.d("GoogleSignIn", "Requesting credential")
            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            val credential = result.credential
            Log.d("GoogleSignIn", "Got credential of type: ${credential.javaClass.simpleName}")

            if(credential is CustomCredential) {
                Log.d("GoogleSignIn", "Credential is CustomCredential, type: ${credential.type}")

                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        Log.d("GoogleSignIn", "Creating GoogleIdTokenCredential")
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(
                            credential.data,
                        )

                        Log.d("GoogleSignIn", "Creating Firebase credential")
                        val firebaseCredential = GoogleAuthProvider.getCredential(
                            googleIdTokenCredential.idToken,
                            null
                        )

                        Log.d("GoogleSignIn", "Signing in with Firebase")
                        auth.signInWithCredential(firebaseCredential)
                            .addOnSuccessListener {
                                Log.d("GoogleSignIn", "Firebase sign-in successful. User: ${it.user?.email}")
                                trySend(AuthResponse.Success)
                            }
                            .addOnFailureListener { exception ->
                                Log.e("GoogleSignIn", "Firebase sign-in failed", exception)
                                trySend(AuthResponse.Error(message = exception.message ?: "Unknown error"))
                            }
                            .addOnCompleteListener {
                                Log.d("GoogleSignIn", "Firebase sign-in complete. Success: ${it.isSuccessful}")
                                if (it.isSuccessful) {
                                    trySend(AuthResponse.Success)
                                } else {
                                    trySend(AuthResponse.Error(message = it.exception?.message ?: "Unknown error"))
                                }
                            }

                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("GoogleSignIn", "Failed to parse Google ID token", e)
                        trySend(AuthResponse.Error(message = e.message ?: "Unknown error with google id token parsing"))
                    }
                } else {
                    Log.e("GoogleSignIn", "Unexpected credential type: ${credential.type}")
                    trySend(AuthResponse.Error(message = "Unexpected credential type"))
                }
            } else {
                Log.e("GoogleSignIn", "Credential is not CustomCredential")
                trySend(AuthResponse.Error(message = "Unexpected credential format"))
            }
        } catch (e: Exception) {
            Log.e("GoogleSignIn", "Error during sign-in process", e)
            trySend(AuthResponse.Error(message = e.message ?: "Unknown error"))
        }
        awaitClose {
            Log.d("GoogleSignIn", "Flow closed")
        }
    }

    fun signOut() { auth.signOut() }

    fun isUserSignedIn() = auth.currentUser != null
}