package com.example.allinone.core.di

import android.content.Context
import com.example.allinone.auth.data.remote.impl.AuthenticationManager
import com.example.allinone.auth.domain.use_case.ValidateEmail
import com.example.allinone.auth.domain.use_case.ValidatePassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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