package com.example.allinone.settings.presentation.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.settings.ThemePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingViewModel @Inject constructor(
    private val themePreferences: ThemePreferences
) : ViewModel() {

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    private val _isReadingModeEnabled = MutableStateFlow(false)
    val isReadingModeEnabled: StateFlow<Boolean> = _isReadingModeEnabled

    init {
        viewModelScope.launch {
            themePreferences.isDarkTheme.collect { isDark ->
                _isDarkTheme.value = isDark
            }
        }

        viewModelScope.launch {
            themePreferences.isReadingTheme.collect { isReading ->
                _isReadingModeEnabled.value = isReading
            }
        }
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