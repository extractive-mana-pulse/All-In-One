package com.example.allinone.core.di

import android.content.Context
import com.example.allinone.core.preferences.ReadingModePreferences
import com.example.allinone.core.preferences.ScheduledMode
import com.example.allinone.core.preferences.ThemePreferences
import com.example.allinone.settings.autoNight.data.remote.repositoryImpl.AutoNightModePreference
import com.example.allinone.settings.autoNight.data.remote.repositoryImpl.LocationRepositoryImpl
import com.example.allinone.settings.autoNight.domain.repository.LocationRepository
import com.example.allinone.settings.batterySafe.data.local.repositoryImpl.BatterySaverPreferences
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
    fun provideAutoNightMode(
        @ApplicationContext context: Context
    ): AutoNightModePreference = AutoNightModePreference(context)

    @Provides
    @Singleton
    fun provideBatterySaverPreferences(
        @ApplicationContext context: Context
    ): BatterySaverPreferences = BatterySaverPreferences(context)

    @Provides
    @Singleton
    fun provideTheme(
        @ApplicationContext context: Context
    ): ThemePreferences = ThemePreferences(context)

    @Provides
    @Singleton
    fun provideReadingMode(
        @ApplicationContext context: Context
    ): ReadingModePreferences = ReadingModePreferences(context)

    @Provides
    @Singleton
    fun provideScheduledMode(
        @ApplicationContext context: Context
    ): ScheduledMode = ScheduledMode(context)

    @Provides
    @Singleton
    fun provideLocationRepository() : LocationRepository = LocationRepositoryImpl()

}