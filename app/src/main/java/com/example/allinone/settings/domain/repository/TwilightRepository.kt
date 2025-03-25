package com.example.allinone.settings.domain.repository

import com.example.allinone.settings.domain.model.Twilight

interface TwilightRepository {

    suspend fun getTwilight(
        latitude: Double,
        longitude: Double
    ): Twilight

}