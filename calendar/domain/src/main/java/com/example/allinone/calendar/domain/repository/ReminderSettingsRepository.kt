package com.example.allinone.calendar.domain.repository

import com.example.allinone.calendar.domain.model.LeadTime
import com.example.allinone.calendar.domain.model.ReminderSettings
import kotlinx.coroutines.flow.Flow

interface ReminderSettingsRepository {
    val settings: Flow<ReminderSettings>
    suspend fun setNotificationsEnabled(enabled: Boolean)
    suspend fun setVibrationEnabled(enabled: Boolean)
    suspend fun setSoundEnabled(enabled: Boolean)
    suspend fun setDefaultLeadTime(leadTime: LeadTime)
}