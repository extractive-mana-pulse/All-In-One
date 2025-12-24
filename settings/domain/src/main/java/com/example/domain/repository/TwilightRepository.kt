package com.example.domain.repository

import com.example.domain.model.Twilight

interface TwilightRepository {

    suspend fun getTwilight(
        latitude: Double,
        longitude: Double
    ): Twilight

}