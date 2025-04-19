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

    /**
     * Updates the user profile with optional display name and photo URI
     * Returns a Flow of AuthResponse to track the operation
     */
    fun updateUserProfile(
        displayName: String? = null,
        photoUri: Uri? = null
    ): Flow<AuthResponse> = callbackFlow {
        val user = auth.currentUser ?: run {
            trySend(AuthResponse.Error("No user is signed in"))
            close()
            return@callbackFlow
        }

        val profileUpdates = UserProfileChangeRequest.Builder().apply {
            displayName?.let { setDisplayName(it) }
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
            // Convert image Uri to byte array
            val imageByteArray = convertImageUriToByteArray(imageUri)
            if (imageByteArray == null || imageByteArray.isEmpty()) {
                trySend(AuthResponse.Error("Failed to process image"))
                close()
                return@callbackFlow
            }

            // Reference to the user's profile picture in storage
            val storageRef = FirebaseStorage.getInstance().reference
                .child("profile_pictures/${user.uid}")

            // Upload byte array with progress tracking
            val metadata = StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build()

            val uploadTask = storageRef.putBytes(imageByteArray, metadata)

            // Send progress updates
            uploadTask.addOnProgressListener { taskSnapshot ->
                val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toInt()
                trySend(AuthResponse.Loading(progress))
            }

            // Handle failures at upload level
            uploadTask.addOnFailureListener { exception ->
                trySend(AuthResponse.Error("Upload failed: ${exception.message}"))
                close()
            }

            // Continue with getting download URL and updating profile
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    throw task.exception ?: Exception("Unknown error during upload")
                }
                storageRef.downloadUrl
            }.addOnSuccessListener { downloadUri ->
                // Log the URL for debugging purposes
                Log.d("ProfileUpload", "Download URL: $downloadUri")

                // Instead of collect, use updateProfile directly with the callback pattern
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

    /**
     * Converts an image URI to a byte array
     * Includes compression to reduce upload size
     */
    private fun convertImageUriToByteArray(imageUri: Uri): ByteArray? {
        return try {
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(imageUri) ?: return null

            // Create a bitmap from the input stream
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()

            // Compress the bitmap to a reasonable size (adjust quality as needed)
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)

            outputStream.toByteArray()
        } catch (e: Exception) {
            Log.e("ImageConversion", "Failed to convert image: ${e.message}")
            null
        }
    }


    fun signInWithGoogle() : Flow<AuthResponse> = callbackFlow {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.web_client_id))
            .setAutoSelectEnabled(false)
            .setNonce(createNonce())
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        try {
            val credentialManager = CredentialManager.create(context)
            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            val credential = result.credential
            if(credential is CustomCredential) {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(
                            credential.data,
                        )

                        val firebaseCredential = GoogleAuthProvider.getCredential(
                            googleIdTokenCredential.idToken,
                            null
                        )

                        auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    trySend(AuthResponse.Success)
                                } else {
                                    trySend(AuthResponse.Error(message = it.exception?.message ?: "Unknown error"))
                                }
                            }

                    } catch (e: GoogleIdTokenParsingException) {
                        trySend(AuthResponse.Error(message = e.message ?: "Unknown error with google id token parsing"))
                    }
                }
            }
        } catch (e : Exception) {
            trySend(AuthResponse.Error(message = e.message ?: "Unknown error"))
        }
        awaitClose()
    }

    fun signOut() { auth.signOut() }

    fun isUserSignedIn() = auth.currentUser != null
}