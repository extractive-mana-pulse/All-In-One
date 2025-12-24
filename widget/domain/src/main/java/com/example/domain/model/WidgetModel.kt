package com.example.domain.model

//@Entity(tableName = "temperature_data")
data class WidgetModel(
//    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val celsius: String,
    val fahrenheit: String,
    val kelvin: String,
)