package com.example.data.firebase

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.allinone.core.presentation.R
import com.example.domain.UserCredentials
import com.example.domain.model.AuthResult
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
    private val db = Firebase.firestore

    init {
        // This will keep the user signed in until explicitly signed out
        auth.addAuthStateListener { firebaseAuth ->
            Log.d(TAG, "Auth state changed. User: ${firebaseAuth.currentUser?.email}")
        }
    }

    /**
     * Create a new account with email and password
     */
    fun createAccountWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<AuthResult> = callbackFlow {
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Account created successfully for: $email")
                        trySend(AuthResult.Success)
                    } else {
                        val errorMessage = task.exception?.message ?: "Unknown error"
                        Log.e(TAG, "Account creation failed: $errorMessage")
                        trySend(AuthResult.Error(message = errorMessage))
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in createAccount: ${e.message}")
            trySend(AuthResult.Error(message = e.message ?: "Unknown error"))
        }
        awaitClose()
    }

    /**
     * Sign in with email and password
     */
    fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<AuthResult> = callbackFlow {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Sign in successful for: $email")
                        trySend(AuthResult.Success)
                    } else {
                        val errorMessage = task.exception?.message ?: "Unknown error"
                        Log.e(TAG, "Sign in failed: $errorMessage")
                        trySend(AuthResult.Error(message = errorMessage))
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in signIn: ${e.message}")
            trySend(AuthResult.Error(message = e.message ?: "Unknown error"))
        }
        awaitClose()
    }

    /**
     * Sign in with Google
     */
    fun signInWithGoogle(): Flow<AuthResult> = callbackFlow {
        Log.d(TAG, "Starting Google sign-in process")

        try {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.web_client_id))
                .setAutoSelectEnabled(false)
                .setNonce(createNonce())
                .build()

            Log.d(TAG, "Using web client ID: ${context.getString(R.string.web_client_id)}")

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val credentialManager = CredentialManager.create(context)

            Log.d(TAG, "Requesting credential")
            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            val credential = result.credential
            Log.d(TAG, "Got credential of type: ${credential.javaClass.simpleName}")

            when {
                credential is CustomCredential &&
                        credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL -> {
                    try {
                        Log.d(TAG, "Creating GoogleIdTokenCredential")
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(
                            credential.data
                        )

                        Log.d(TAG, "Creating Firebase credential")
                        val firebaseCredential = GoogleAuthProvider.getCredential(
                            googleIdTokenCredential.idToken,
                            null
                        )

                        Log.d(TAG, "Signing in with Firebase")
                        auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "Firebase sign-in successful. User: ${task.result?.user?.email}")
                                    trySend(AuthResult.Success)
                                } else {
                                    val errorMessage = task.exception?.message ?: "Unknown error"
                                    Log.e(TAG, "Firebase sign-in failed: $errorMessage")
                                    trySend(AuthResult.Error(message = errorMessage))
                                }
                            }
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Failed to parse Google ID token", e)
                        trySend(AuthResult.Error(message = e.message ?: "Google ID token parsing error"))
                    }
                }
                else -> {
                    Log.e(TAG, "Unexpected credential type: ${credential.javaClass.simpleName}")
                    trySend(AuthResult.Error(message = "Unexpected credential type"))
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error during Google sign-in process", e)
            trySend(AuthResult.Error(message = e.message ?: "Google sign-in failed"))
        }

        awaitClose {
            Log.d(TAG, "Google sign-in flow closed")
        }
    }

    /**
     * Get currently signed in user
     */
    fun getSignedInUser(): UserCredentials? {
        return auth.currentUser?.run {
            UserCredentials(
                id = uid,
                email = email ?: "",
                displayName = displayName ?: "",
                imageUrl = photoUrl?.toString()
            )
        }
    }

    /**
     * Fetch user data from Firestore (with fallback to auth user)
     */
    fun fetchUserData(): Flow<UserCredentials?> = callbackFlow {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    val basicUser = getSignedInUser()
                    if (document.exists() && basicUser != null) {
                        // You can add custom fields from Firestore here
                        val firestoreDisplayName = document.getString("displayName")
                        val firestoreImageUrl = document.getString("imageUrl")

                        trySend(
                            basicUser.copy(
                                displayName = firestoreDisplayName ?: basicUser.displayName,
                                imageUrl = firestoreImageUrl ?: basicUser.imageUrl
                            )
                        )
                    } else {
                        trySend(basicUser)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "Failed to fetch user data from Firestore: ${exception.message}")
                    trySend(getSignedInUser())
                }
        } else {
            trySend(null)
        }
        awaitClose()
    }

    /**
     * Update user display name and/or photo
     */
    fun updateDisplayNameProfile(
        photoUri: Uri? = null,
        displayName: String? = null
    ): Flow<AuthResult> = callbackFlow {
        val profileUpdatesBuilder = UserProfileChangeRequest.Builder()
        var hasChanges = false

        val user = auth.currentUser
        if (user == null) {
            trySend(AuthResult.Error(message = "No user is signed in"))
            close()
            return@callbackFlow
        }

        photoUri?.let {
            profileUpdatesBuilder.photoUri = it
            hasChanges = true
        }

        displayName?.let {
            profileUpdatesBuilder.displayName = it
            hasChanges = true
        }

        if (!hasChanges) {
            trySend(AuthResult.Success)
            close()
            return@callbackFlow
        }

        val profileUpdates = profileUpdatesBuilder.build()

        user.updateProfile(profileUpdates)
            .addOnSuccessListener {
                Log.d(TAG, "Profile updated successfully")

                // Also update Firestore if needed
                val updates = mutableMapOf<String, Any>()
                displayName?.let { updates["displayName"] = it }
                photoUri?.let { updates["imageUrl"] = it.toString() }

                if (updates.isNotEmpty()) {
                    db.collection("users").document(user.uid)
                        .set(updates)
                        .addOnSuccessListener {
                            Log.d(TAG, "Firestore updated successfully")
                        }
                        .addOnFailureListener { e ->
                            Log.e(TAG, "Failed to update Firestore: ${e.message}")
                        }
                }

                trySend(AuthResult.Success)
                close()
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Profile update failed: ${exception.message}")
                trySend(AuthResult.Error(exception.message ?: "Profile update failed"))
                close()
            }
        awaitClose()
    }

    /**
     * Upload profile picture to Firebase Storage
     */
    fun uploadProfilePicture(imageUri: Uri): Flow<AuthResult> = callbackFlow {
        val user = auth.currentUser
        if (user == null) {
            trySend(AuthResult.Error(message = "No user is signed in"))
            close()
            return@callbackFlow
        }

        if (imageUri == Uri.EMPTY) {
            trySend(AuthResult.Error(message = "No image selected"))
            close()
            return@callbackFlow
        }

        try {
            val imageByteArray = convertImageUriToByteArray(context, imageUri)

            if (imageByteArray == null || imageByteArray.isEmpty()) {
                trySend(AuthResult.Error(message = "Failed to process image"))
                close()
                return@callbackFlow
            }

            val storageRef = FirebaseStorage.getInstance().reference
                .child("profile_images/${user.uid}/${System.currentTimeMillis()}.jpg")

            val metadata = StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build()

            val uploadTask = storageRef.putBytes(imageByteArray, metadata)

            uploadTask.addOnProgressListener { taskSnapshot ->
                val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toInt()
                trySend(AuthResult.Loading(progress))
            }

            uploadTask.addOnFailureListener { exception ->
                Log.e(TAG, "Upload failed: ${exception.message}")
                trySend(AuthResult.Error(message = "Upload failed: ${exception.message}"))
                close()
            }

            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    throw task.exception ?: Exception("Unknown error during upload")
                }
                storageRef.downloadUrl
            }.addOnSuccessListener { downloadUri ->
                Log.d(TAG, "Upload successful. Download URL: $downloadUri")

                // Update Firebase Auth profile
                user.updateProfile(
                    UserProfileChangeRequest.Builder()
                        .setPhotoUri(downloadUri)
                        .build()
                ).addOnSuccessListener {
                    // Also update Firestore
                    db.collection("users").document(user.uid)
                        .update("imageUrl", downloadUri.toString())
                        .addOnSuccessListener {
                            Log.d(TAG, "Profile photo updated in Firestore")
                        }
                        .addOnFailureListener { e ->
                            Log.e(TAG, "Failed to update Firestore: ${e.message}")
                        }

                    trySend(AuthResult.Success)
                    close()
                }.addOnFailureListener { exception ->
                    Log.e(TAG, "Failed to update profile: ${exception.message}")
                    trySend(AuthResult.Error(message = "Failed to update profile: ${exception.message}"))
                    close()
                }
            }.addOnFailureListener { exception ->
                Log.e(TAG, "Failed to get download URL: ${exception.message}")
                trySend(AuthResult.Error(message = "Failed to get download URL: ${exception.message}"))
                close()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Upload process error: ${e.message}")
            trySend(AuthResult.Error(message = "Upload process error: ${e.message}"))
            close()
        }
        awaitClose()
    }

    /**
     * Delete profile image from Firebase Storage
     */
    fun deleteImageFromFirebaseStorage(userId: String) {
        val imagePath = "profile_images/$userId"

        val storage = FirebaseStorage.getInstance()
        val folderRef = storage.reference.child(imagePath)

        // List all files in the user's folder
        folderRef.listAll()
            .addOnSuccessListener { listResult ->
                listResult.items.forEach { fileRef ->
                    fileRef.delete()
                        .addOnSuccessListener {
                            Log.d(TAG, "Image deleted successfully: ${fileRef.path}")
                        }
                        .addOnFailureListener { exception ->
                            Log.e(TAG, "Failed to delete image: ${exception.message}")
                        }
                }

                // Also remove from Firestore
                db.collection("users").document(userId)
                    .update("imageUrl", null)
                    .addOnSuccessListener {
                        Log.d(TAG, "Image URL removed from Firestore")
                    }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Failed to list images: ${exception.message}")
            }
    }

    /**
     * Send password reset email
     */
    fun sendPasswordResetEmail(email: String): Flow<AuthResult> = callbackFlow {
        if (email.isBlank()) {
            trySend(AuthResult.Error(message = "Email cannot be empty"))
            close()
            return@callbackFlow
        }

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Password reset email sent to: $email")
                    trySend(AuthResult.Success)
                } else {
                    val errorMessage = task.exception?.message ?: "Failed to send reset email"
                    Log.e(TAG, "Password reset failed: $errorMessage")
                    trySend(AuthResult.Error(message = errorMessage))
                }
            }
        awaitClose()
    }

    /**
     * Sign out current user
     */
    fun signOut() {
        auth.signOut()
        Log.d(TAG, "User signed out")
    }

    /**
     * Check if user is signed in
     */
    fun isUserSignedIn(): Boolean = auth.currentUser != null

    /**
     * Create a nonce for Google Sign In
     */
    private fun createNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

    /**
     * Convert image URI to ByteArray with compression
     */
    private fun convertImageUriToByteArray(
        context: Context,
        uri: Uri,
        quality: Int = 80
    ): ByteArray? {
        return try {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            }

            // Compress to reduce file size
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            val byteArray = outputStream.toByteArray()

            bitmap.recycle()
            outputStream.close()

            Log.d(TAG, "Image converted to ByteArray. Size: ${byteArray.size / 1024} KB")
            byteArray
        } catch (e: Exception) {
            Log.e(TAG, "Failed to convert image: ${e.message}", e)
            null
        }
    }

    companion object {
        private const val TAG = "AuthenticationManager"
    }
}