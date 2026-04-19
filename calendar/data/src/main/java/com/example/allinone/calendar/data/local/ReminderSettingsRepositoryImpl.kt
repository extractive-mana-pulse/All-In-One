package com.example.allinone.calendar.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.allinone.calendar.domain.model.LeadTime
import com.example.allinone.calendar.domain.model.ReminderSettings
import com.example.allinone.calendar.domain.repository.ReminderSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.reminderSettingsStore: DataStore<Preferences>
        by preferencesDataStore(name = "reminder_settings")

class ReminderSettingsRepositoryImpl(
    private val context: Context,
) : ReminderSettingsRepository {

    private object Keys {
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val VIBRATION_ENABLED = booleanPreferencesKey("vibration_enabled")
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val DEFAULT_LEAD_TIME_MINUTES = intPreferencesKey("default_lead_time_minutes")
    }

    override val settings: Flow<ReminderSettings> =
        context.reminderSettingsStore.data.map { prefs ->
            ReminderSettings(
                notificationsEnabled = prefs[Keys.NOTIFICATIONS_ENABLED] ?: true,
                vibrationEnabled = prefs[Keys.VIBRATION_ENABLED] ?: true,
                soundEnabled = prefs[Keys.SOUND_ENABLED] ?: true,
                defaultLeadTime = LeadTime.fromMinutes(
                    prefs[Keys.DEFAULT_LEAD_TIME_MINUTES] ?: LeadTime.AT_TIME.minutes,
                ),
            )
        }

    override suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.reminderSettingsStore.edit { it[Keys.NOTIFICATIONS_ENABLED] = enabled }
    }

    override suspend fun setVibrationEnabled(enabled: Boolean) {
        context.reminderSettingsStore.edit { it[Keys.VIBRATION_ENABLED] = enabled }
    }

    override suspend fun setSoundEnabled(enabled: Boolean) {
        context.reminderSettingsStore.edit { it[Keys.SOUND_ENABLED] = enabled }
    }

    override suspend fun setDefaultLeadTime(leadTime: LeadTime) {
        context.reminderSettingsStore.edit {
            it[Keys.DEFAULT_LEAD_TIME_MINUTES] = leadTime.minutes
        }
    }
}