package com.example.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.get

class BatterySaverPreferences(
    private val context: Context
) {
    companion object {
        private val BATTERY_SAFER = stringPreferencesKey("auto_night_mode")
        private val BATTERY_SLIDER_KEY = floatPreferencesKey("slider_value")
    }

    val sliderFlow: Flow<Float> = context.dataStore.data
        .map { preferences ->
            preferences[BATTERY_SLIDER_KEY] ?: 0.5f
        }

    suspend fun saveSliderValue(value: Float) {
        context.dataStore.edit { preferences ->
            preferences[BATTERY_SLIDER_KEY] = value
        }
    }
}