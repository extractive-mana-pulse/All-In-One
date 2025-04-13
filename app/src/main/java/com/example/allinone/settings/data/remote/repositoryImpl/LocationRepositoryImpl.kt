package com.example.allinone.settings.data.remote.repositoryImpl

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Build
import com.example.allinone.settings.domain.repository.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.Locale

class LocationRepositoryImpl: LocationRepository {

    override fun getAddressFromLocation(
        context: Context,
        location: Location,
        onAddressFetched: (String?) -> Unit
    ) {
        val geocoder = Geocoder(context, Locale.getDefault())
        val latitude = location.latitude
        val longitude = location.longitude

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // Use the new API for Android 13+
                geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                    if (addresses.isNotEmpty()) {
                        val address = addresses[0].getAddressLine(0)
                        onAddressFetched(address)
                    } else {
                        onAddressFetched("No address found")
                    }
                }
            } else {
                // Use the deprecated method for older Android versions
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0].getAddressLine(0)
                    onAddressFetched(address)
                } else {
                    onAddressFetched("No address found")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onAddressFetched("Error fetching address: ${e.message}")
        }
    }

    @SuppressLint("MissingPermission")
    override fun fetchLocationAndAddress(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient,
        onAddressFetched: (String?) -> Unit
    ) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                // Use the repository's own method
                getAddressFromLocation(
                    context,
                    location,
                    onAddressFetched
                )
            } ?: onAddressFetched("Location not found")
        }.addOnFailureListener {
            onAddressFetched("Error fetching location: ${it.message}")
        }
    }
}