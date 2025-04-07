package com.example.allinone.settings.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.main.data.repository.TimerRepository
import com.example.allinone.settings.ThemePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingViewModel @Inject constructor(
    private val themePreferences: ThemePreferences,
    private val timerRepository: TimerRepository
) : ViewModel() {

    val timer = timerRepository.timer.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = 0L
    )

    fun startTimer() {
        viewModelScope.launch {
            timerRepository.startTimer()
        }
    }

    fun stopTimer() {
        timerRepository.stopTimer()
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }

    fun readingModeSnackbar(seconds: Int): Boolean {
        return timer.value >= seconds
    }

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme = _isDarkTheme
        .onStart {
            isDarkTheme()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    private val _isReadingModeEnabled = MutableStateFlow(false)
    val isReadingModeEnabled = _isReadingModeEnabled
        .onStart {
            isReadingTheme()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    private fun isDarkTheme() {
        viewModelScope.launch {
            themePreferences.isDarkTheme.collect { isDark ->
                _isDarkTheme.value = isDark
            }
        }
    }

    fun isReadingModeActive(): Boolean {
        return _isReadingModeEnabled.value
    }

    private fun isReadingTheme() {
        viewModelScope.launch {
            themePreferences.isReadingTheme.collectLatest { isReading ->
                _isReadingModeEnabled.value = isReading
            }
        }
    }
    fun enableReadingMode() {
        _isReadingModeEnabled.value = true
    }

    fun disableReadingMode() {
        _isReadingModeEnabled.value = false
    }

    fun toggleDarkTheme(enabled: Boolean) {
        viewModelScope.launch {
            themePreferences.saveThemePreference(enabled)
        }
    }

    fun toggleReadingMode(enabled: Boolean) {
        viewModelScope.launch {
            themePreferences.saveReadingModePreference(enabled)
        }
    }
}