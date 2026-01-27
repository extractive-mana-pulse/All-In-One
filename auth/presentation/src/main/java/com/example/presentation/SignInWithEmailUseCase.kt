package com.example.presentation

import com.example.domain.UserCredentials
import com.example.domain.model.AuthResult
import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<AuthResult> {
        return repository.signInWithEmailAndPassword(email, password)
    }
}

class SignUpWithEmailUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<AuthResult> {
        return repository.createAccountWithEmailAndPassword(email, password)
    }
}

class SignInWithGoogleUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<AuthResult> {
        return repository.signInWithGoogle()
    }
}

class SignOutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke() {
        repository.signOut()
    }
}

class GetSignedInUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): UserCredentials? {
        return repository.getSignedInUser()
    }
}

class FetchUserDataUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<UserCredentials?> {
        return repository.fetchUserData()
    }
}

class IsUserSignedInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Boolean {
        return repository.isUserSignedIn()
    }
}

class UpdateDisplayNameUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(
        photoUri: String? = null,
        displayName: String? = null
    ): Flow<AuthResult> {
        return repository.updateDisplayNameProfile(photoUri, displayName)
    }
}

class UploadProfilePictureUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(imageUriString: String): Flow<AuthResult> {
        return repository.uploadProfilePicture(imageUriString)
    }
}

class DeleteProfileImageUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(userId: String) {
        repository.deleteImageFromFirebaseStorage(userId)
    }
}

//class SendPasswordResetEmailUseCase @Inject constructor(
//    private val repository: AuthRepository
//) {
//    operator fun invoke(email: String): Flow<AuthResult> {
//        return repository.sendPasswordResetEmail(email)
//    }
//}