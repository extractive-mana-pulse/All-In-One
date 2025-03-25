package com.example.allinone.settings.domain.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.allinone.settings.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AutoNightModeRepository(private val context: Context) {
    companion object {
        private val AUTO_NIGHT_MODE_KEY = stringPreferencesKey("auto_night_mode")
    }

    val selectedModeFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[AUTO_NIGHT_MODE_KEY] ?: "disabled"
        }

    suspend fun saveSelectedMode(mode: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTO_NIGHT_MODE_KEY] = mode
        }
    }
}