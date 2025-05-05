package com.example.allinone.settings.deviceTemp.domain.model

data class SensorReading(
    val celsius: Float,
    val fahrenheit: Float,
    val kelvin: Float,
    val accuracy: Int,
    val lastUpdated: Long
)
