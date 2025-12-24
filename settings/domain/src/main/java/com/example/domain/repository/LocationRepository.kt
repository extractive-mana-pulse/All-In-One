package com.example.domain.repository

import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient

interface LocationRepository {

    fun getAddressFromLocation(
        context: Context,
        location: Location,
        onAddressFetched: (String?) -> Unit
    )

    fun fetchLocationAndAddress(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient,
        onAddressFetched: (String?) -> Unit
    )
}