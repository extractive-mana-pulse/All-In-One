package com.example.domain.repository

import com.example.domain.UserCredentials
import com.example.domain.model.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun createAccountWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<AuthResult>

    fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<AuthResult>

    fun signInWithGoogle(): Flow<AuthResult>

    fun getSignedInUser(): UserCredentials?

    fun fetchUserData(): Flow<UserCredentials?>

    fun updateDisplayNameProfile(
        photoUri: String? = null,
        displayName: String? = null
    ): Flow<AuthResult>

    fun uploadProfilePicture(imageUri: String): Flow<AuthResult>

    fun deleteImageFromFirebaseStorage(userId: String)

    fun signOut()

    fun isUserSignedIn(): Boolean
}