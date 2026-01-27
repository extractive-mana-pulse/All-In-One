package com.example.data

import com.example.domain.TemperatureData

fun TemperatureDataDTO.toTemperatureData(): TemperatureData {
    return TemperatureData(
        id = id,
        temperature = temperature,
        timestamp = timestamp
    )
}

fun TemperatureData.toTemperatureDataDTO(): TemperatureDataDTO {
    return TemperatureDataDTO(
        id = id,
        temperature = temperature,
        timestamp = timestamp
    )
}