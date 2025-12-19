package com.example.data.remote.repositoryImpl

import com.example.allinone.settings.autoNight.data.remote.api.TwilightApi
import com.example.allinone.settings.autoNight.domain.model.Twilight
import com.example.allinone.settings.autoNight.domain.repository.TwilightRepository

class TwilightRepositoryImpl(private val api: TwilightApi): TwilightRepository {

    override suspend fun getTwilight(
        latitude: Double,
        longitude: Double
    ): Twilight {
        return api.getTwilight(latitude, longitude)
    }
}