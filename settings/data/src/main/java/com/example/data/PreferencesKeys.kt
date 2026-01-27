package com.example.data

import androidx.datastore.preferences.core.booleanPreferencesKey

object PreferencesKeys {
    val DARK_THEME = booleanPreferencesKey("dark_theme")
    val READING_THEME = booleanPreferencesKey("reading_theme")
    val SCHEDULED_MODE = booleanPreferencesKey("scheduled_mode")
}