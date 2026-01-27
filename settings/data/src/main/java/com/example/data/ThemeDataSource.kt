package com.example.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.data.remote.repositoryImpl.dataStore
import com.example.domain.ThemeDataSourceRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeDataSource(
    private val context: Context
): ThemeDataSourceRepo {

    override val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.DARK_THEME] == true
        }

    override suspend fun saveThemePreference(isDarkTheme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_THEME] = isDarkTheme
        }
    }
}