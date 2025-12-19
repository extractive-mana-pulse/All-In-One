package com.example.data.remote.api

import com.example.domain.model.Twilight
import retrofit2.http.GET
import retrofit2.http.Query

interface TwilightApi {

    @GET("json")
    suspend fun getTwilight(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("time_format") timeFormat: String = "24",
    ): Twilight
}