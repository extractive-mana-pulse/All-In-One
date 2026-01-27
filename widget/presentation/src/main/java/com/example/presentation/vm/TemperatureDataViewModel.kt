package com.example.presentation.vm

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.TemperatureData
import com.example.domain.repository.GlanceWidgetRepository
import com.example.presentation.screens.DeviceTempWidget
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemperatureViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: GlanceWidgetRepository
) : ViewModel() {

    fun getTemp() = repository.getTemperature()

    fun insertTemperature(temperatureData: TemperatureData) {
        viewModelScope.launch {
            repository.insertTemperature(temperatureData)
            DeviceTempWidget().updateAll(context)
        }
    }

    fun deleteTemperature(temperatureData: TemperatureData) {
        viewModelScope.launch {
            repository.deleteTemperature(temperatureData)
            DeviceTempWidget().updateAll(context)
        }
    }
}