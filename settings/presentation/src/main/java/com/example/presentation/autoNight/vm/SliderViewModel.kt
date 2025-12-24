package com.example.presentation.autoNight.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.settings.autoNight.data.remote.repositoryImpl.AutoNightModePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SliderViewModel @Inject constructor(
    private val autoNightModeRepository: AutoNightModePreference
) : ViewModel() {

    val sliderValue = autoNightModeRepository.sliderFlow

    fun updateSliderValue(newValue: Float) {
        viewModelScope.launch {
            autoNightModeRepository.saveSliderValue(newValue)
        }
    }
}
