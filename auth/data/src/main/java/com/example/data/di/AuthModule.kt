package com.example.data.di

import com.example.data.firebase.GoogleAuthUiClient
import com.example.domain.use_case.ValidateEmail
import com.example.domain.repository.GoogleAuthUiClientRepo
import com.example.domain.use_case.ValidatePassword
import com.example.domain.use_case.ValidateRepeatedPassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
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

    @Provides
    @Singleton
    fun provideGoogleAuthUiClient(): GoogleAuthUiClientRepo = GoogleAuthUiClient()
}