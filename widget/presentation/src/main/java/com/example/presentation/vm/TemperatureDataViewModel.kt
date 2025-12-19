package com.example.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.settings.deviceTemp.domain.model.TemperatureData
import com.example.allinone.widget.domain.repository.TempRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemperatureDataViewModel @Inject constructor(
    private val tempRepository: TempRepository
) : ViewModel() {

    private val _tempData = MutableStateFlow(TemperatureData())
    val tempData = _tempData.asStateFlow()

    fun insertTemperature(sensorData: TemperatureData) {
        viewModelScope.launch {
            tempRepository.uploadTemperatureData(sensorData)
        }
    }

    fun getTemperature() {
        viewModelScope.launch {
            tempRepository.getDeviceTemp().collect {
                _tempData.value = it
            }
        }
    }
}