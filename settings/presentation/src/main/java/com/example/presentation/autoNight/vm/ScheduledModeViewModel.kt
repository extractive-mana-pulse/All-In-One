package com.example.presentation.autoNight.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduledModeViewModel @Inject constructor(
    private val scheduledModePreferences: ScheduleRepository
): ViewModel() {

    private val _isScheduledModeEnabled = MutableStateFlow(false)
    val isScheduledMode = _isScheduledModeEnabled
        .onStart {
            isScheduledMode()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000L),
            initialValue = false
        )

    private fun isScheduledMode() {
        viewModelScope.launch {
            scheduledModePreferences.isScheduledMode.collectLatest { isReading ->
                _isScheduledModeEnabled.value = isReading
            }
        }
    }

    fun toggleScheduledMode(enabled: Boolean) {
        viewModelScope.launch {
            scheduledModePreferences.saveScheduledModePreference(enabled)
        }
    }

    fun disableScheduledMode(disabled: Boolean) {
        viewModelScope.launch {
            scheduledModePreferences.removeScheduledModePreference(disabled)
        }
    }
}