package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.allinone.settings.deviceTemp.domain.model.TemperatureData
import kotlinx.coroutines.flow.Flow

@Dao
interface TempDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceTemp(temperatureData: TemperatureData)

    @Query("SELECT * FROM temperature_data")
    fun getDeviceTemp(): Flow<TemperatureData>

    @Delete
    suspend fun deleteDeviceTemp(temperatureData: TemperatureData)

}