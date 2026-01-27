package com.example.domain

import kotlinx.coroutines.flow.Flow

interface ReadingModePreferenceRepo {
    val isReadingTheme: Flow<Boolean>
    suspend fun saveReadingModePreference(isReadingMode: Boolean)
    suspend fun removeReadingModePreference(isReadingMode: Boolean)
}