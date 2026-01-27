package com.example.data.di

import android.content.Context
import com.example.data.firebase.AuthRepositoryImpl
import com.example.data.firebase.AuthenticationManager
import com.example.domain.ValidateEmail
import com.example.domain.repository.AuthRepository
import com.example.domain.use_case.ValidatePassword
import com.example.domain.use_case.ValidateRepeatedPassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthenticationManager(
        @ApplicationContext context: Context
    ): AuthenticationManager {
        return AuthenticationManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authenticationManager: AuthenticationManager
    ): AuthRepository {
        return AuthRepositoryImpl(authenticationManager)
    }

    // Authentication Use Cases
//    @Provides
//    fun provideSignInWithEmailUseCase(
//        repository: AuthRepository
//    ): SignInWithEmailUseCase {
//        return SignInWithEmailUseCase(repository)
//    }

//    @Provides
//    fun provideSignUpWithEmailUseCase(
//        repository: AuthRepository
//    ): SignUpWithEmailUseCase {
//        return SignUpWithEmailUseCase(repository)
//    }

//    @Provides
//    fun provideSignInWithGoogleUseCase(
//        repository: AuthRepository
//    ): SignInWithGoogleUseCase {
//        return SignInWithGoogleUseCase(repository)
//    }
//
//    @Provides
//    fun provideSignOutUseCase(
//        repository: AuthRepository
//    ): SignOutUseCase {
//        return SignOutUseCase(repository)
//    }
//
//    @Provides
//    fun provideGetSignedInUserUseCase(
//        repository: AuthRepository
//    ): GetSignedInUserUseCase {
//        return GetSignedInUserUseCase(repository)
//    }
//
//    @Provides
//    fun provideFetchUserDataUseCase(
//        repository: AuthRepository
//    ): FetchUserDataUseCase {
//        return FetchUserDataUseCase(repository)
//    }
//
//    @Provides
//    fun provideIsUserSignedInUseCase(
//        repository: AuthRepository
//    ): IsUserSignedInUseCase {
//        return IsUserSignedInUseCase(repository)
//    }
//
//    @Provides
//    fun provideUpdateDisplayNameUseCase(
//        repository: AuthRepository
//    ): UpdateDisplayNameUseCase {
//        return UpdateDisplayNameUseCase(repository)
//    }
//
//    @Provides
//    fun provideUploadProfilePictureUseCase(
//        repository: AuthRepository
//    ): UploadProfilePictureUseCase {
//        return UploadProfilePictureUseCase(repository)
//    }
//
//    @Provides
//    fun provideDeleteProfileImageUseCase(
//        repository: AuthRepository
//    ): DeleteProfileImageUseCase {
//        return DeleteProfileImageUseCase(repository)
//    }

//    @Provides
//    fun provideSendPasswordResetEmailUseCase(
//        repository: AuthRepository
//    ): SendPasswordResetEmailUseCase {
//        return SendPasswordResetEmailUseCase(repository)
//    }

//     Validation Use Cases
    @Provides
    fun provideValidateEmail(): ValidateEmail {
        return ValidateEmail()
    }

    @Provides
    fun provideValidatePassword(): ValidatePassword {
        return ValidatePassword()
    }

    @Provides
    fun provideValidateRepeatedPassword(): ValidateRepeatedPassword {
        return ValidateRepeatedPassword()
    }
}