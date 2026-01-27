package com.example.domain

import kotlinx.coroutines.flow.Flow

interface ThemeDataSourceRepo {

    val isDarkTheme: Flow<Boolean>

    suspend fun saveThemePreference(isDarkTheme: Boolean)

}