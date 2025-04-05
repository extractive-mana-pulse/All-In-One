package com.example.allinone.settings.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.settings.ThemePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingViewModel @Inject constructor(
    private val themePreferences: ThemePreferences
) : ViewModel() {

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

    private fun isReadingTheme() {
        viewModelScope.launch {
            themePreferences.isReadingTheme.collect { isReading ->
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