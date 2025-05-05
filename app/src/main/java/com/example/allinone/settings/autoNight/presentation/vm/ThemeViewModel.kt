package com.example.allinone.settings.autoNight.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.settings.autoNight.domain.model.Twilight
import com.example.allinone.settings.autoNight.data.remote.repositoryImpl.AutoNightModePreference
import com.example.allinone.settings.autoNight.domain.repository.TwilightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val repository: TwilightRepository,
    private val autoNightModeRepository: AutoNightModePreference
) : ViewModel() {

    private val _selectedMode = MutableStateFlow("disabled")
    val selectedMode = _selectedMode
        .onStart {
            selectMode()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = "disabled"
        )

    private val _twilight = MutableStateFlow(Twilight())
    val twilight = _twilight.asStateFlow()

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

    private fun selectMode() {
        viewModelScope.launch {
            autoNightModeRepository.selectedModeFlow.collectLatest { mode ->
                _selectedMode.value = mode
            }
        }
    }

    fun selectItem(mode: String) {
        viewModelScope.launch {
            autoNightModeRepository.saveSelectedMode(mode)
        }
    }
}
