package com.example.data.di

import android.content.Context
import com.example.data.firebase.AuthenticationManager
import com.example.domain.use_case.ValidateEmail
import com.example.domain.use_case.ValidatePassword
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthenticationManager(
        @ApplicationContext context: Context
    ): AuthenticationManager = AuthenticationManager(context)

    @Provides
    @Singleton
    fun provideEmailValidation() : ValidateEmail = ValidateEmail()

    @Provides
    @Singleton
    fun providePasswordValidation() : ValidatePassword = ValidatePassword()
}