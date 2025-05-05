package com.example.allinone.widget.domain.repository

import com.example.allinone.settings.deviceTemp.domain.model.TemperatureData
import com.example.allinone.widget.data.TempDao
import javax.inject.Inject

class TempRepository @Inject constructor(
    private val tempDao: TempDao,
    private val glanceWidgetRepository: GlanceWidgetRepository
) {
    fun getDeviceTemp() = tempDao.getDeviceTemp()

    suspend fun uploadTemperatureData(temperatureData: TemperatureData) {
        tempDao.insertDeviceTemp(temperatureData)
        glanceWidgetRepository.tempInserted()
    }

    suspend fun deleteTemperatureData(temperatureData: TemperatureData) {
        tempDao.deleteDeviceTemp(temperatureData)
        glanceWidgetRepository.tempDeleted()
    }
}