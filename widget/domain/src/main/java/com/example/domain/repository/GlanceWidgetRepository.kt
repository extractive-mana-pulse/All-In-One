package com.example.domain.repository

import com.example.domain.TemperatureData
import kotlinx.coroutines.flow.Flow

interface GlanceWidgetRepository {
    fun getTemperature(): Flow<TemperatureData>
    suspend fun insertTemperature(temperatureData: TemperatureData)
    suspend fun deleteTemperature(temperatureData: TemperatureData)
}