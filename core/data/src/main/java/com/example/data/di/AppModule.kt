package com.example.data.di

import android.content.Context
import com.example.data.RteRepositoryImpl
import com.example.domain.RteRepositoryRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRteRepositoryImpl(
        @ApplicationContext context: Context
    ): RteRepositoryRepo = RteRepositoryImpl(context)
}