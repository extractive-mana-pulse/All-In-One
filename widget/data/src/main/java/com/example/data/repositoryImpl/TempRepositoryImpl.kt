package com.example.data.repositoryImpl

import com.example.allinone.settings.deviceTemp.domain.model.TemperatureData
import com.example.allinone.widget.data.TempDao
import javax.inject.Inject

class TempRepositoryImpl @Inject constructor(
    private val tempDao: TempDao,
    private val glanceWidgetRepository: GlanceWidgetRepositoryImpl
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