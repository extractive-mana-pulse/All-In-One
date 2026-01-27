package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TempDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceTemp(temperatureDataDTO: TemperatureDataDTO)

    @Query("SELECT * FROM temperature_data ORDER BY timestamp DESC LIMIT 1")
    fun getDeviceTemp(): Flow<TemperatureDataDTO?>

    @Delete
    suspend fun deleteDeviceTemp(temperatureDataDTO: TemperatureDataDTO)
}