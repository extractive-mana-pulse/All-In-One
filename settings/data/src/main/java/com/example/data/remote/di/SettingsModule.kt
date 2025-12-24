package com.example.data.remote.di

import android.content.Context
import com.example.data.remote.api.TwilightApi
import com.example.data.remote.repositoryImpl.AutoNightModePreference
import com.example.data.remote.repositoryImpl.LocationRepositoryImpl
import com.example.data.remote.repositoryImpl.TwilightRepositoryImpl
import com.example.domain.repository.LocationRepository
import com.example.domain.repository.TwilightRepository
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
            .baseUrl(BuildConfig.BASE_URL)
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