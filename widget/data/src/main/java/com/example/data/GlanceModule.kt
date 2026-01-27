package com.example.data

import android.content.Context
import androidx.room.Room
import com.example.data.repositoryImpl.GlanceWidgetRepositoryImpl
import com.example.domain.repository.GlanceWidgetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GlanceModule {

    @Provides
    @Singleton
    fun provideGlanceDatabase(
        @ApplicationContext context: Context
    ): GlanceDatabase {
        return Room.databaseBuilder(
            context,
            GlanceDatabase::class.java,
            "temperature_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTempDao(database: GlanceDatabase): TempDao {
        return database.tempDao()
    }

    @Provides
    @Singleton
    fun provideGlanceWidgetRepository(
        dao: TempDao
    ): GlanceWidgetRepository {
        return GlanceWidgetRepositoryImpl(dao)
    }
}