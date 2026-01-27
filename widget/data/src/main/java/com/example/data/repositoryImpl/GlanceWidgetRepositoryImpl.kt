package com.example.data.repositoryImpl

import com.example.data.TempDao
import com.example.data.toTemperatureData
import com.example.data.toTemperatureDataDTO
import com.example.domain.TemperatureData
import com.example.domain.repository.GlanceWidgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GlanceWidgetRepositoryImpl @Inject constructor(
    private val dao: TempDao,
) : GlanceWidgetRepository {

    override fun getTemperature(): Flow<TemperatureData> {
        return dao.getDeviceTemp().map { dto ->
            dto?.toTemperatureData() ?: TemperatureData(temperature = 0f)
        }
    }

    override suspend fun insertTemperature(temperatureData: TemperatureData) {
        dao.insertDeviceTemp(temperatureData.toTemperatureDataDTO())
    }

    override suspend fun deleteTemperature(temperatureData: TemperatureData) {
        dao.deleteDeviceTemp(temperatureData.toTemperatureDataDTO())
    }
}