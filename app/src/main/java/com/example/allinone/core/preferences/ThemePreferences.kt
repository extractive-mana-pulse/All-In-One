package com.example.allinone.core.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object PreferencesKeys {
    val DARK_THEME = booleanPreferencesKey("dark_theme")
    val READING_THEME = booleanPreferencesKey("reading_theme")
}

class ThemePreferences(private val context: Context) {

    val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.DARK_THEME] == true
        }

    val isReadingTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.READING_THEME] == true
        }

    suspend fun saveThemePreference(isDarkTheme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_THEME] = isDarkTheme
        }
    }

    suspend fun saveReadingModePreference(isReadingMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.READING_THEME] = isReadingMode
        }
    }
}
