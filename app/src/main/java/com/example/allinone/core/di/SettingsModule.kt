package com.example.allinone.core.di

import android.content.Context
import com.example.allinone.core.preferences.ReadingModePreferences
import com.example.allinone.core.preferences.ThemePreferences
import com.example.allinone.settings.autoNight.data.remote.repositoryImpl.LocationRepositoryImpl
import com.example.allinone.settings.autoNight.domain.repository.AutoNightModeRepository
import com.example.allinone.settings.autoNight.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun provideAutoNightModeRepository(
        @ApplicationContext context: Context
    ): AutoNightModeRepository = AutoNightModeRepository(context)

    @Provides
    @Singleton
    fun provideThemeRepository(
        @ApplicationContext context: Context
    ): ThemePreferences = ThemePreferences(context)

    @Provides
    @Singleton
    fun provideReadingModeRepository(
        @ApplicationContext context: Context
    ): ReadingModePreferences = ReadingModePreferences(context)

    @Provides
    @Singleton
    fun provideLocationRepository() : LocationRepository = LocationRepositoryImpl()

}