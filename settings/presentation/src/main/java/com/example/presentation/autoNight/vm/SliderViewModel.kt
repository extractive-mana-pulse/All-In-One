package com.example.presentation.autoNight.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.AutoNightModePreferenceRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SliderViewModel @Inject constructor(
    private val autoNightModeRepository: AutoNightModePreferenceRepo
) : ViewModel() {

    val sliderValue = autoNightModeRepository.sliderFlow

    fun updateSliderValue(newValue: Float) {
        viewModelScope.launch {
            autoNightModeRepository.saveSliderValue(newValue)
        }
    }
}
