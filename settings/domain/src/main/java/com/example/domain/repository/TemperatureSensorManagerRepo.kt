package com.example.domain.repository

import com.example.domain.TemperatureData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface TemperatureSensorManagerRepo {
    val temperatureData: StateFlow<TemperatureData>
    val sensorAvailable: Boolean

    fun startMonitoring(scope: CoroutineScope)
    fun refreshTemperature()
    fun stopMonitoring()
    fun getAllSensorNames(): String
}