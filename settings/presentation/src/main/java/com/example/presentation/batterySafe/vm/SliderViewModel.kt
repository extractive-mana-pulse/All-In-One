package com.example.presentation.batterySafe.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.BatterySaverPreferencesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BatterySliderViewModel @Inject constructor(
    private val batterySaverPreferences: BatterySaverPreferencesRepo
) : ViewModel() {

    val sliderValue = batterySaverPreferences.sliderFlow

    fun updateSliderValue(newValue: Float) {
        viewModelScope.launch {
            batterySaverPreferences.saveSliderValue(newValue)
        }
    }
}
