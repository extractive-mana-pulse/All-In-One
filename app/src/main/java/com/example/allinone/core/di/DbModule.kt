package com.example.allinone.core.di

import android.content.Context
import androidx.room.Room
import com.example.allinone.widget.data.GlanceDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideAppDb(@ApplicationContext context: Context): GlanceDatabase {
        return Room.databaseBuilder(
            context,
            GlanceDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideTempDao(appDb: GlanceDatabase) = appDb.tempDao()
}