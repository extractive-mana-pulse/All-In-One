package com.example.data.repositoryImpl

import com.example.data.TempDao
import com.example.data.toTemperatureData
import com.example.data.toTemperatureDataDTO
import com.example.domain.TemperatureData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TempRepositoryImpl @Inject constructor(
    private val tempDao: TempDao
) {
    fun getDeviceTemp(): Flow<TemperatureData?> {
        return tempDao.getDeviceTemp().map { it?.toTemperatureData() }
    }

    suspend fun uploadTemperatureData(temperatureData: TemperatureData) {
        withContext(Dispatchers.IO) {
            try {
                tempDao.insertDeviceTemp(temperatureData.toTemperatureDataDTO())
            } catch (e: Exception) {
                println("Error inserting device temperature: ${e.message}")
            }
        }
    }

    suspend fun deleteTemperatureData(temperatureData: TemperatureData) {
        withContext(Dispatchers.IO) {
            try {
                tempDao.deleteDeviceTemp(temperatureData.toTemperatureDataDTO())
            } catch (e: Exception) {
                println("Error deleting device temperature: ${e.message}")
            }
        }
    }
}