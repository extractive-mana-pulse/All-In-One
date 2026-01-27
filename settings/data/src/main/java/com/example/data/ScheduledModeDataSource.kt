package com.example.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.data.remote.repositoryImpl.dataStore
import com.example.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ScheduledModeDataSource(
    private val context: Context
): ScheduleRepository {
    override val isScheduledMode: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.SCHEDULED_MODE] == true
        }

    override suspend fun saveScheduledModePreference(isScheduledMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SCHEDULED_MODE] = isScheduledMode
        }
    }

    override suspend fun removeScheduledModePreference(isScheduledMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SCHEDULED_MODE] = isScheduledMode
        }
    }
}