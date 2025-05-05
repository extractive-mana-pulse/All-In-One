package com.example.allinone.widget.domain.repository

import android.content.Context
import androidx.glance.appwidget.updateAll
import com.example.allinone.settings.deviceTemp.domain.model.TemperatureData
import com.example.allinone.widget.data.TempDao
import com.example.allinone.widget.presentation.screens.DeviceTempWidget
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class GlanceWidgetRepository @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val dao: TempDao,

) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface GlanceWidgetRepositoryEntryPoint {
        fun widgetRepository() : GlanceWidgetRepository
    }

    companion object {
        fun get(applicationContext: Context): GlanceWidgetRepository {

            val widgetModelRepositoryEntryPoint : GlanceWidgetRepositoryEntryPoint =
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