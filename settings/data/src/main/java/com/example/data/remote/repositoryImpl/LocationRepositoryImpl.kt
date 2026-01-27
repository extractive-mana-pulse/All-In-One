package com.example.data.remote.repositoryImpl

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Build
import com.example.domain.model.LocationResult
import com.example.domain.repository.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationRepository {

    override suspend fun getAddressFromLocation(
        latitude: Double,
        longitude: Double
    ): Result<String> = suspendCancellableCoroutine { continuation ->
        val geocoder = Geocoder(context, Locale.getDefault())

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                    if (addresses.isNotEmpty()) {
                        val address = addresses[0].getAddressLine(0)
                        continuation.resume(Result.success(address))
                    } else {
                        continuation.resume(Result.failure(Exception("No address found")))
                    }
                }
            } else {
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0].getAddressLine(0)
                    continuation.resume(Result.success(address))
                } else {
                    continuation.resume(Result.failure(Exception("No address found")))
                }
            }
        } catch (e: Exception) {
            continuation.resume(Result.failure(e))
        }
    }

    @SuppressLint("MissingPermission")
    override suspend fun fetchCurrentLocationAddress(): Flow<LocationResult> = callbackFlow {
        trySend(LocationResult.Loading)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            geocoder.getFromLocation(
                                location.latitude,
                                location.longitude,
                                1
                            ) { addresses ->
                                if (addresses.isNotEmpty()) {
                                    val address = addresses[0].getAddressLine(0)
                                    trySend(LocationResult.Success(address))
                                } else {
                                    trySend(LocationResult.Error("No address found"))
                                }
                            }
                        } else {
                            @Suppress("DEPRECATION")
                            val addresses = geocoder.getFromLocation(
                                location.latitude,
                                location.longitude,
                                1
                            )
                            if (!addresses.isNullOrEmpty()) {
                                val address = addresses[0].getAddressLine(0)
                                trySend(LocationResult.Success(address))
                            } else {
                                trySend(LocationResult.Error("No address found"))
                            }
                        }
                    } catch (e: Exception) {
                        trySend(LocationResult.Error("Error fetching address: ${e.message}"))
                    }
                } else {
                    trySend(LocationResult.Error("Location not available"))
                }
            }
            .addOnFailureListener { exception ->
                trySend(LocationResult.Error("Error fetching location: ${exception.message}"))
            }

        awaitClose()
    }
}