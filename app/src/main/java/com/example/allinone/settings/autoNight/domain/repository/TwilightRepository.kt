package com.example.allinone.settings.autoNight.domain.repository

import com.example.allinone.settings.autoNight.domain.model.Twilight

interface TwilightRepository {

    suspend fun getTwilight(
        latitude: Double,
        longitude: Double
    ): Twilight

}