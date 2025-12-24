package com.example.data

import android.content.Context

@Module
@InstallIn(SingletonComponent::class)
object GlanceModule {

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