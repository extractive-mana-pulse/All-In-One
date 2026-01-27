package com.example.domain

@Entity(tableName = "temperature_data")
data class TemperatureData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val celsius: Float = 0.0f,
    val fahrenheit: String = "--",
    val kelvin: String = "--",
    val sensorName: String = "Unknown",
    val lastUpdated: String = "Not yet updated",
    val accuracy: Int = 0
)