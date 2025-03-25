package com.example.allinone.settings.di

import android.content.Context
import com.example.allinone.settings.domain.repository.AutoNightModeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AutoNightModeModule {

    @Provides
    @Singleton
    fun provideAutoNightModeRepository(
        @ApplicationContext context: Context
    ): AutoNightModeRepository = AutoNightModeRepository(context)
}