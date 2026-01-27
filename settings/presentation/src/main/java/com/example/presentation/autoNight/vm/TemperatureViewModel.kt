package com.example.presentation.autoNight.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.TemperatureData
import com.example.domain.repository.TemperatureSensorManagerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TemperatureViewModel @Inject constructor(
    private val temperatureSensorManager: TemperatureSensorManagerRepo
) : ViewModel() {

    val temperatureData: StateFlow<TemperatureData> = temperatureSensorManager.temperatureData
    val sensorAvailable: Boolean get() = temperatureSensorManager.sensorAvailable
    val allSensorNames: String get() = temperatureSensorManager.getAllSensorNames()

    init {
        startMonitoring()
    }

    private fun startMonitoring() {
        temperatureSensorManager.startMonitoring(viewModelScope)
    }

    fun refreshTemperature() {
        temperatureSensorManager.refreshTemperature()
    }

    override fun onCleared() {
        super.onCleared()
        temperatureSensorManager.stopMonitoring()
    }
}