package com.example.data.repositoryImpl

import android.content.Context
import com.example.data.TempDao
import com.example.domain.TemperatureData
import com.example.presentation.screens.DeviceTempWidget
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class GlanceWidgetRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val appContext: Context,
    private val dao: TempDao,
    ) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface GlanceWidgetRepositoryEntryPoint {
        fun widgetRepository() : GlanceWidgetRepositoryImpl
    }

    companion object Companion {
        fun get(applicationContext: Context): GlanceWidgetRepositoryImpl {

            val widgetModelRepositoryEntryPoint : com.example.data.repositoryImpl.GlanceWidgetRepository.GlanceWidgetRepositoryEntryPoint =
                EntryPoints.get(
                    applicationContext,
                    GlanceWidgetRepositoryEntryPoint::class.java
                )
            return widgetModelRepositoryEntryPoint.widgetRepository()
        }
    }

    suspend fun tempInserted() = DeviceTempWidget().updateAll(appContext)

    suspend fun tempDeleted() = DeviceTempWidget().updateAll(appContext)

    fun getTemp() : Flow<TemperatureData> = dao.getDeviceTemp().distinctUntilChanged()
}