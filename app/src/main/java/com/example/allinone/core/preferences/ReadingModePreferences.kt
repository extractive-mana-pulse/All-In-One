package com.example.allinone.core.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.allinone.core.extension.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReadingModePreferences(
    private val context: Context
) {

    val isReadingTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.READING_THEME] == true
        }

    suspend fun saveReadingModePreference(isReadingMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.READING_THEME] = isReadingMode
        }
    }

    suspend fun removeReadingModePreference(isReadingMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.READING_THEME] = isReadingMode
        }
    }
}