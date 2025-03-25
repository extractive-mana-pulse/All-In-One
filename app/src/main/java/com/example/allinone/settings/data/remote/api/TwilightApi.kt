package com.example.allinone.settings.data.remote.api

import com.example.allinone.settings.domain.model.Twilight
import retrofit2.http.GET
import retrofit2.http.Query

interface TwilightApi {

    @GET("json")
    suspend fun getTwilight(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double
    ): Twilight
}