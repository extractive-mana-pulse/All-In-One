package com.example.data.remote

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.data.PreferencesKeys
import com.example.data.remote.repositoryImpl.dataStore
import com.example.domain.ReadingModePreferenceRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReadingModePreferences(
    private val context: Context
): ReadingModePreferenceRepo {

    override val isReadingTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.READING_THEME] == true
        }

    override suspend fun saveReadingModePreference(isReadingMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.READING_THEME] = isReadingMode
        }
    }

    override suspend fun removeReadingModePreference(isReadingMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.READING_THEME] = isReadingMode
        }
    }
}