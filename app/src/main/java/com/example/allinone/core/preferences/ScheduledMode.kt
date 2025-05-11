package com.example.allinone.core.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.allinone.core.extension.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ScheduledMode(
    private val context: Context
) {
    val isScheduledMode: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.SCHEDULED_MODE] == true
        }

    suspend fun saveScheduledModePreference(isScheduledMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SCHEDULED_MODE] = isScheduledMode
        }
    }

    suspend fun removeScheduledModePreference(isScheduledMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SCHEDULED_MODE] = isScheduledMode
        }
    }
}