package com.example.allinone.settings.data.remote.repositoryImpl

import com.example.allinone.settings.data.remote.api.TwilightApi
import com.example.allinone.settings.domain.model.Twilight
import com.example.allinone.settings.domain.repository.TwilightRepository

class TwilightRepositoryImpl(private val api: TwilightApi): TwilightRepository {

    override suspend fun getTwilight(
        latitude: Double,
        longitude: Double
    ): Twilight {
        return api.getTwilight(latitude, longitude)
    }
}