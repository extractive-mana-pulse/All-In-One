package com.example.allinone.main.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.core.preferences.ReadingModePreferences
import com.example.allinone.main.presentation.details.DetailScreenAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val readingModePreferences: ReadingModePreferences
): ViewModel() {

    private val _timer = MutableStateFlow(0L)
    val timer = _timer.asStateFlow()

    private var timerJob: Job? = null

    private val _shouldShowReadingModeSnackbar = MutableStateFlow(false)
    val shouldShowReadingModeSnackbar = _shouldShowReadingModeSnackbar.asStateFlow()

    fun onAction(action: DetailScreenAction) {
        when(action) {
            DetailScreenAction.OnNavigateAway -> stopTimer()
            DetailScreenAction.OnSnackbarDismiss -> stopTimer()
            is DetailScreenAction.OnCourseLoaded -> startTimer()
            DetailScreenAction.OnSnackbarShown -> _shouldShowReadingModeSnackbar.value = false
            is DetailScreenAction.OnSnackbarActionPerformed -> toggleReadingMode(action.enabled)
            else -> Unit
        }
    }

    fun toggleReadingMode(enabled: Boolean) {
        viewModelScope.launch {
            readingModePreferences.saveReadingModePreference(enabled)
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        _timer.value = 0
        _shouldShowReadingModeSnackbar.value = false // Reset snackbar flag

        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _timer.value++

                // Automatically trigger snackbar state when threshold is reached
                if (_timer.value >= SNACKBAR_THRESHOLD && !_shouldShowReadingModeSnackbar.value) {
                    _shouldShowReadingModeSnackbar.value = true
                    timerJob?.cancel() // Stop timer when threshold is reached
                }
            }
        }
    }

    private fun stopTimer() {
        _timer.value = 0
        _shouldShowReadingModeSnackbar.value = false
        timerJob?.cancel()
    }

    fun readingModeSnackbar(targetSeconds: Int): Boolean {
        startTimer()
        return if (timer.value >= targetSeconds) {
            timerJob?.cancel()
            true
        } else {
            false
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

    companion object {

        const val SNACKBAR_THRESHOLD = 15

    }
}