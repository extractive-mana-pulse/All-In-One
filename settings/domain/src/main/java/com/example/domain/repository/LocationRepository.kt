package com.example.domain.repository

import com.example.domain.model.LocationResult
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getAddressFromLocation(
        latitude: Double,
        longitude: Double
    ): Result<String>

    suspend fun fetchCurrentLocationAddress(): Flow<LocationResult>
}