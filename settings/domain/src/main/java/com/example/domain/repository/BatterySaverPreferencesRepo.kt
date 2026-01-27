package com.example.domain.repository

import kotlinx.coroutines.flow.Flow

interface BatterySaverPreferencesRepo {

    val sliderFlow: Flow<Float>
    suspend fun saveSliderValue(value: Float)
}