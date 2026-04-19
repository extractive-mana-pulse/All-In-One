package com.example.allinone.calendar.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.calendar.domain.model.LeadTime
import com.example.allinone.calendar.domain.model.ReminderSettings
import com.example.allinone.calendar.domain.repository.ReminderSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationSettingsViewModel @Inject constructor(
    private val repository: ReminderSettingsRepository,
) : ViewModel() {

    val settings: StateFlow<ReminderSettings> = repository.settings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ReminderSettings())

    fun setNotificationsEnabled(enabled: Boolean) = viewModelScope.launch {
        repository.setNotificationsEnabled(enabled)
    }

    fun setVibrationEnabled(enabled: Boolean) = viewModelScope.launch {
        repository.setVibrationEnabled(enabled)
    }

    fun setSoundEnabled(enabled: Boolean) = viewModelScope.launch {
        repository.setSoundEnabled(enabled)
    }

    fun setDefaultLeadTime(leadTime: LeadTime) = viewModelScope.launch {
        repository.setDefaultLeadTime(leadTime)
    }
}
