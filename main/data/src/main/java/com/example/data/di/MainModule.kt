package com.example.data.di

import android.content.Context

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

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