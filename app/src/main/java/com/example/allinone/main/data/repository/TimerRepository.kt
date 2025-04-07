package com.example.allinone.main.data.repository

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimerRepository {
    private val _timer = MutableStateFlow(0L)
    val timer = _timer.asStateFlow()

    private var timerJob: Job? = null

    suspend fun startTimer() {
        stopTimer()
        timerJob = coroutineScope {
            launch {
                while (true) {
                    delay(1000)
                    _timer.value++
                }
            }
        }
    }

    fun stopTimer() = timerJob?.cancel()
}