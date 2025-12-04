package com.example.allinone.core.di

import android.content.Context
import com.example.allinone.BuildConfig
import com.example.allinone.main.data.remote.DetailsRepositoryImpl
import com.example.allinone.main.data.remote.HomeRepositoryImpl
import com.example.allinone.main.domain.repository.DetailsRepository
import com.example.allinone.main.domain.repository.HomeRepository
import com.example.allinone.settings.autoNight.data.remote.api.TwilightApi
import com.example.allinone.settings.autoNight.data.remote.repositoryImpl.TwilightRepositoryImpl
import com.example.allinone.settings.autoNight.domain.repository.TwilightRepository
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
object AppModule {

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
    fun provideDetailsRepository(
        @ApplicationContext context: Context
    ) : DetailsRepository = DetailsRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideHomeRepository(
        @ApplicationContext context: Context
    ) : HomeRepository = HomeRepositoryImpl(context)
}