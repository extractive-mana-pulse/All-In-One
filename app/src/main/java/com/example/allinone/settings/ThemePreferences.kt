package com.example.allinone.settings

// Create this in a separate file like ThemePreferences.kt
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property for Context to access the DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

// Preference keys
object PreferencesKeys {
    val DARK_THEME = booleanPreferencesKey("dark_theme")
}

// Theme preferences repository
class ThemePreferences(private val context: Context) {
    // Get the theme preference as a Flow
    val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.DARK_THEME] ?: false
        }

    // Update the theme preference
    suspend fun saveThemePreference(isDarkTheme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_THEME] = isDarkTheme
        }
    }
}