package com.example.domain

data class TemperatureData(
    val id: Int = 0,
    val temperature: Float = 0f,
    val timestamp: Long = System.currentTimeMillis()
)