package com.example.allinone.settings.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.core.preferences.ThemePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingModeViewModel @Inject constructor(
    private val themePreferences: ThemePreferences
) : ViewModel() {

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
    
    private fun isReadingTheme() {
        viewModelScope.launch {
            themePreferences.isReadingTheme.collectLatest { isReading ->
                _isReadingModeEnabled.value = isReading
            }
        }
    }

    fun toggleReadingMode(enabled: Boolean) {
        viewModelScope.launch {
            themePreferences.saveReadingModePreference(enabled)
        }
    }

    fun disableReadingMode() {
        _isReadingModeEnabled.value = false
    }
}