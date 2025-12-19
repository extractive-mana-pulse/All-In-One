package com.example.presentation.autoNight.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Twilight
import com.example.domain.repository.TwilightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

// function of this view model is to get the twilight data from the repository
// and update the _twilight state with the new data.
// also this view model selects an auto night mode from the repository
// so the name of this should be kinda: AutoNightModeSelector or smth like that
// also we've to take twilight from this view model and move it to separate view model

@HiltViewModel
class AutoNightViewModel @Inject constructor(
    private val repository: TwilightRepository,
//    private val autoNightModeRepository: AutoNightModePreference
) : ViewModel() {

    private val _twilight = MutableStateFlow(Twilight())
    val twilight = _twilight.asStateFlow()

    private val _selectedMode = MutableStateFlow("disabled")
    val selectedMode = _selectedMode
        .onStart {
//            observeSelectedMode()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = "disabled"
        )

    fun getTwilight(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val response = repository.getTwilight(latitude, longitude)
                _twilight.value = response
            } catch (e: Exception) {
                Log.e("ThemeViewModel", "Error fetching twilight data", e)
            }
        }
    }

//    private fun observeSelectedMode() {
//        viewModelScope.launch {
//            autoNightModeRepository.selectedModeFlow.collectLatest { mode ->
//                _selectedMode.value = mode
//            }
//        }
//    }

//    fun selectMode(mode: String) {
//        viewModelScope.launch {
//            autoNightModeRepository.saveSelectedMode(mode)
//        }
//    }
}