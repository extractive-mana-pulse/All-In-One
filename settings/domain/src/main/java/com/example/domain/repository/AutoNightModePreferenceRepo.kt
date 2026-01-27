package com.example.domain.repository

import kotlinx.coroutines.flow.Flow

interface AutoNightModePreferenceRepo {

    val sliderFlow: Flow<Float>

    suspend fun saveSliderValue(value: Float)

    val selectedModeFlow: Flow<String>

    suspend fun saveSelectedMode(mode: String)

}