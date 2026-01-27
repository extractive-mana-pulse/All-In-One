package com.example.data.remote.repositoryImpl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.repository.AutoNightModePreferenceRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class AutoNightModePreference(
    private val context: Context
) : AutoNightModePreferenceRepo {

    companion object {
        private val AUTO_NIGHT_MODE_KEY = stringPreferencesKey("auto_night_mode")
        private val SLIDER_KEY = floatPreferencesKey("slider_value")
    }

    override val sliderFlow: Flow<Float> = context.dataStore.data
        .map { preferences ->
            preferences[SLIDER_KEY] ?: 0.5f
        }

    override suspend fun saveSliderValue(value: Float) {
        context.dataStore.edit { preferences ->
            preferences[SLIDER_KEY] = value
        }
    }

    override val selectedModeFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[AUTO_NIGHT_MODE_KEY] ?: "disabled"
        }

    override suspend fun saveSelectedMode(mode: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTO_NIGHT_MODE_KEY] = mode
        }
    }
}