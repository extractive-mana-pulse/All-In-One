package com.example.data.di

import android.content.Context
import com.example.data.repositoryImpl.DetailsRepositoryImpl
import com.example.data.repositoryImpl.HomeRepositoryImpl
import com.example.domain.repository.DetailsRepository
import com.example.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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