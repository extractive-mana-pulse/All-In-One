package com.example.allinone.settings.presentation.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReadingModeViewModel : ViewModel() {

    // StateFlow to hold the reading mode state that can be collected throughout the app
    private val _isReadingModeEnabled = MutableStateFlow(false)
    val isReadingModeEnabled: StateFlow<Boolean> = _isReadingModeEnabled.asStateFlow()

    // For Compose UI state
    private val _uiState = mutableStateOf(ReadingModeUiState())
    val uiState: State<ReadingModeUiState> = _uiState

    // Initialize ViewModel (e.g., load saved preference)
    init {
        // You might want to load the saved reading mode preference here
        // For example, using DataStore:
        // viewModelScope.launch {
        //     dataStore.data.collect { preferences ->
        //         val savedMode = preferences[IS_READING_MODE_ENABLED] ?: false
        //         _isReadingModeEnabled.value = savedMode
        //         _uiState.value = _uiState.value.copy(isReadingModeEnabled = savedMode)
        //     }
        // }
    }

    // Toggle reading mode
    fun toggleReadingMode(enabled: Boolean) {
        _isReadingModeEnabled.value = enabled
        _uiState.value = _uiState.value.copy(isReadingModeEnabled = enabled)

        // Save preference if needed
        // viewModelScope.launch {
        //     dataStore.edit { preferences ->
        //         preferences[IS_READING_MODE_ENABLED] = enabled
        //     }
        // }
    }
}

// UI state for reading mode
data class ReadingModeUiState(
    val isReadingModeEnabled: Boolean = false
)
