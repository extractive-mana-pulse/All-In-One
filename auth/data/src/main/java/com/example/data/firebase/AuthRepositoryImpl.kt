package com.example.data.firebase

import android.net.Uri
import android.util.Log
import com.example.domain.UserCredentials
import com.example.domain.model.AuthResult
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementation of AuthRepository that bridges domain layer (String-based)
 * with data layer (Uri-based Firebase implementation)
 */
class AuthRepositoryImpl @Inject constructor(
    private val authenticationManager: AuthenticationManager
) : AuthRepository {

    override fun createAccountWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<AuthResult> {
        Log.d(TAG, "Creating account for email: $email")
        return authenticationManager
            .createAccountWithEmailAndPassword(email, password)
            .catch { exception ->
                Log.e(TAG, "Create account error: ${exception.message}")
                emit(AuthResult.Error(message = exception.message ?: "Failed to create account"))
            }
    }

    override fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<AuthResult> {
        Log.d(TAG, "Signing in with email: $email")
        return authenticationManager
            .signInWithEmailAndPassword(email, password)
            .catch { exception ->
                Log.e(TAG, "Sign in error: ${exception.message}")
                emit(AuthResult.Error(message = exception.message ?: "Failed to sign in"))
            }
    }

    override fun signInWithGoogle(): Flow<AuthResult> {
        Log.d(TAG, "Signing in with Google")
        return authenticationManager
            .signInWithGoogle()
            .catch { exception ->
                Log.e(TAG, "Google sign in error: ${exception.message}")
                emit(AuthResult.Error(message = exception.message ?: "Google sign in failed"))
            }
    }

    override fun getSignedInUser(): UserCredentials? {
        return try {
            authenticationManager.getSignedInUser()
        } catch (e: Exception) {
            Log.e(TAG, "Get signed in user error: ${e.message}")
            null
        }
    }

    override fun fetchUserData(): Flow<UserCredentials?> {
        Log.d(TAG, "Fetching user data")
        return authenticationManager
            .fetchUserData()
            .catch { exception ->
                Log.e(TAG, "Fetch user data error: ${exception.message}")
                emit(null)
            }
    }

    override fun updateDisplayNameProfile(
        photoUri: String?,
        displayName: String?
    ): Flow<AuthResult> {
        Log.d(TAG, "Updating profile - displayName: $displayName, photoUri: ${photoUri != null}")

        // Convert String to Uri if provided
        val uri = photoUri?.let { uriString ->
            try {
                Uri.parse(uriString)
            } catch (e: Exception) {
                Log.e(TAG, "Invalid URI string: $uriString")
                null
            }
        }

        return authenticationManager
            .updateDisplayNameProfile(uri, displayName)
            .catch { exception ->
                Log.e(TAG, "Update profile error: ${exception.message}")
                emit(AuthResult.Error(message = exception.message ?: "Failed to update profile"))
            }
    }

    override fun uploadProfilePicture(imageUriString: String): Flow<AuthResult> {
        Log.d(TAG, "Uploading profile picture from URI: $imageUriString")

        return try {
            // Convert String to Uri
            val uri = Uri.parse(imageUriString)

            if (uri == Uri.EMPTY) {
                Log.e(TAG, "Invalid URI: $imageUriString")
                flow {
                    emit(AuthResult.Error(message = "Invalid image URI"))
                }
            } else {
                authenticationManager
                    .uploadProfilePicture(uri)
                    .catch { exception ->
                        Log.e(TAG, "Upload error: ${exception.message}")
                        emit(AuthResult.Error(message = exception.message ?: "Upload failed"))
                    }
            }
        } catch (e: Exception) {
            Log.e(TAG, "URI parsing error: ${e.message}")
            flow {
                emit(AuthResult.Error(message = "Invalid URI format: ${e.message}"))
            }
        }
    }

    override fun deleteImageFromFirebaseStorage(userId: String) {
        Log.d(TAG, "Deleting profile image for user: $userId")
        try {
            authenticationManager.deleteImageFromFirebaseStorage(userId)
        } catch (e: Exception) {
            Log.e(TAG, "Delete image error: ${e.message}")
        }
    }

//    override fun sendPasswordResetEmail(email: String): Flow<AuthResult> {
//        Log.d(TAG, "Sending password reset email to: $email")
//        return authenticationManager
//            .sendPasswordResetEmail(email)
//            .catch { exception ->
//                Log.e(TAG, "Password reset error: ${exception.message}")
//                emit(AuthResult.Error(message = exception.message ?: "Failed to send reset email"))
//            }
//    }

    override fun signOut() {
        Log.d(TAG, "Signing out user")
        try {
            authenticationManager.signOut()
        } catch (e: Exception) {
            Log.e(TAG, "Sign out error: ${e.message}")
        }
    }

    override fun isUserSignedIn(): Boolean {
        return try {
            authenticationManager.isUserSignedIn()
        } catch (e: Exception) {
            Log.e(TAG, "Check sign in status error: ${e.message}")
            false
        }
    }

    companion object {
        private const val TAG = "AuthRepositoryImpl"
    }
}