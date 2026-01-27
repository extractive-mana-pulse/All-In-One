package com.example.domain.repository

import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {

    val isScheduledMode: Flow<Boolean>

    suspend fun saveScheduledModePreference(isScheduledMode: Boolean)

    suspend fun removeScheduledModePreference(isScheduledMode: Boolean)
}
