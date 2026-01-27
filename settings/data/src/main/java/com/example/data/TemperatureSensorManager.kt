package com.example.data

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.domain.TemperatureData
import com.example.domain.repository.TemperatureSensorManagerRepo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class TemperatureSensorManager @Inject constructor(
    @ApplicationContext private val context: Context
) : TemperatureSensorManagerRepo {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val _temperatureData = MutableStateFlow(TemperatureData())
    override val temperatureData: StateFlow<TemperatureData> = _temperatureData.asStateFlow()

    private var sensorEventListener: SensorEventListener? = null
    private var updateJob: Job? = null
    private var lastUIUpdateTime = 0L

    private val allTempSensors: List<Sensor> = mutableListOf<Sensor>().apply {
        sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)?.let { add(it) }
        sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE)?.let { add(it) }
        sensorManager.getSensorList(Sensor.TYPE_ALL).forEach { sensor ->
            if (sensor.name.contains("temp", ignoreCase = true) && !contains(sensor)) {
                add(sensor)
            }
        }
    }

    private val allSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

    override val sensorAvailable: Boolean
        get() = allTempSensors.isNotEmpty()

    override fun startMonitoring(scope: CoroutineScope) {
        if (allTempSensors.isEmpty()) return

        val tempSensor = allTempSensors.first()

        sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val celsius = event.values[0]
                val fahrenheit = (celsius * 9 / 5) + 32
                val kelvin = celsius + 273.15f
                val currentTime = System.currentTimeMillis()

                // Update UI every 10 minutes or on first reading
                if (currentTime - lastUIUpdateTime >= 10 * 60 * 1000 || lastUIUpdateTime == 0L) {
                    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                    lastUIUpdateTime = currentTime

                    _temperatureData.update {
                        it.copy(
                            id = it.id + 1,
                            temperature = celsius,
                            timestamp = currentTime,
                        )
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                // Handle accuracy changes if needed
            }
        }

        sensorManager.registerListener(
            sensorEventListener,
            tempSensor,
            SensorManager.SENSOR_DELAY_UI
        )

        // Schedule periodic update every 10 minutes
        updateJob = scope.launch {
            while (isActive) {
                delay(10 * 60 * 1000)
                lastUIUpdateTime = 0L
            }
        }
    }

    override fun refreshTemperature() {
        lastUIUpdateTime = 0L
        sensorEventListener?.let {
            sensorManager.unregisterListener(it)
            allTempSensors.firstOrNull()?.let { sensor ->
                sensorManager.registerListener(
                    it,
                    sensor,
                    SensorManager.SENSOR_DELAY_UI
                )
            }
        }
    }

    override fun stopMonitoring() {
        sensorEventListener?.let {
            sensorManager.unregisterListener(it)
        }
        updateJob?.cancel()
        sensorEventListener = null
        updateJob = null
    }

    override fun getAllSensorNames(): String = allSensors.joinToString("\n") { it.name }
}