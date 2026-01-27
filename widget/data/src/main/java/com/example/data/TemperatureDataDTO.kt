package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "temperature_data")
data class TemperatureData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val temperature: Float,
    val timestamp: Long = System.currentTimeMillis()
)