package com.example.data.remote.di

import android.content.Context
import com.example.data.BatterySaverPreferences
import com.example.data.LanguageManager
import com.example.data.ScheduledModeDataSource
import com.example.data.TemperatureSensorManager
import com.example.data.ThemeDataSource
import com.example.data.remote.ReadingModePreferences
import com.example.data.remote.api.TwilightApi
import com.example.data.remote.repositoryImpl.AutoNightModePreference
import com.example.data.remote.repositoryImpl.LocationRepositoryImpl
import com.example.data.remote.repositoryImpl.TwilightRepositoryImpl
import com.example.domain.ReadingModePreferenceRepo
import com.example.domain.ThemeDataSourceRepo
import com.example.domain.repository.AutoNightModePreferenceRepo
import com.example.domain.repository.BatterySaverPreferencesRepo
import com.example.domain.repository.LanguageManagerRepo
import com.example.domain.repository.LocationRepository
import com.example.domain.repository.ScheduleRepository
import com.example.domain.repository.TemperatureSensorManagerRepo
import com.example.domain.repository.TwilightRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun provideTwilightApi() : TwilightApi {
        return Retrofit.Builder()
            .baseUrl(com.example.allinone.settings.data.BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .build()
            .create(TwilightApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTwilightRepository(
        api: TwilightApi
    ) : TwilightRepository = TwilightRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideTemperatureSensorManagerRepository(
        @ApplicationContext context: Context
    ) : TemperatureSensorManagerRepo = TemperatureSensorManager(context)


    @Provides
    @Singleton
    fun provideAutoNightMode(
        @ApplicationContext context: Context
    ): AutoNightModePreferenceRepo = AutoNightModePreference(context)

    @Provides
    @Singleton
    fun provideBatterySaverPreferences(
        @ApplicationContext context: Context
    ): BatterySaverPreferencesRepo = BatterySaverPreferences(context)

    @Provides
    @Singleton
    fun provideTheme(
        @ApplicationContext context: Context
    ): ThemeDataSourceRepo = ThemeDataSource(context)

    @Provides
    @Singleton
    fun provideReadingMode(
        @ApplicationContext context: Context
    ): ReadingModePreferenceRepo = ReadingModePreferences(context)

    @Provides
    @Singleton
    fun provideScheduledMode(
        @ApplicationContext context: Context
    ): ScheduleRepository = ScheduledModeDataSource(context)

    @Provides
    @Singleton
    fun provideFusedLocationClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(
        @ApplicationContext context: Context,
        fusedLocationClient: FusedLocationProviderClient
    ): LocationRepository {
        return LocationRepositoryImpl(context, fusedLocationClient)
    }
    @Provides
    @Singleton
    fun provideLanguageManager(
        @ApplicationContext context: Context
    ): LanguageManagerRepo {
        return LanguageManager(context)
    }
}