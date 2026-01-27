package com.example.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.ReadingModePreferenceRepo
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
    private val readingModePreferences: ReadingModePreferenceRepo
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
            readingModePreferences.isReadingTheme.collectLatest { isReading ->
                _isReadingModeEnabled.value = isReading
            }
        }
    }

    fun toggleReadingMode(enabled: Boolean) {
        viewModelScope.launch {
            readingModePreferences.saveReadingModePreference(enabled)
        }
    }

    fun disableReadingMode(disabled: Boolean) {
        viewModelScope.launch {
            readingModePreferences.removeReadingModePreference(disabled)
        }
    }
}