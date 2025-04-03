package com.example.allinone.settings.domain.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.allinone.settings.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AutoNightModeRepository(private val context: Context) {
    companion object {
        private val AUTO_NIGHT_MODE_KEY = stringPreferencesKey("auto_night_mode")
    }

    private val SLIDER_KEY = floatPreferencesKey("slider_value")

    val sliderFlow: Flow<Float> = context.dataStore.data
        .map { preferences ->
            preferences[SLIDER_KEY] ?: 0.5f
        }

    // Save slider value
    suspend fun saveSliderValue(value: Float) {
        context.dataStore.edit { preferences ->
            preferences[SLIDER_KEY] = value
        }
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