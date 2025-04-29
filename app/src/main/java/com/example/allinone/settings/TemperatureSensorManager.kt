package com.example.allinone.settings

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TemperatureSensorManager.kt
class TemperatureSensorManager(private val context: Context) {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val _temperatureData = MutableStateFlow(TemperatureData())
    val temperatureData: StateFlow<TemperatureData> = _temperatureData

    private var sensorEventListener: SensorEventListener? = null
    private var updateJob: Job? = null

    // Track last update time to prevent constant UI updates
    private var lastUIUpdateTime = 0L

    // Get all temperature sensors from the device
    val allTempSensors = mutableListOf<Sensor>().apply {
        sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)?.let { add(it) }
        sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE)?.let { add(it) }
        sensorManager.getSensorList(Sensor.TYPE_ALL).forEach { sensor ->
            if (sensor.name.contains("temp", ignoreCase = true) &&
                !contains(sensor)) {
                add(sensor)
            }
        }
    }

    // Get all sensors
    val allSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

    val sensorAvailable: Boolean
        get() = allTempSensors.isNotEmpty()

    // Map to store all sensor readings
    private val _allSensorReadings = MutableStateFlow<Map<Sensor, SensorReading>>(emptyMap())
    val allSensorReadings: StateFlow<Map<Sensor, SensorReading>> = _allSensorReadings

    fun startMonitoring(scope: CoroutineScope) {
        if (allTempSensors.isEmpty()) return

        val tempSensor = allTempSensors.first()
        _temperatureData.update { it.copy(sensorName = tempSensor.name) }

        sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val celsius = event.values[0]
                val fahrenheit = (celsius * 9/5) + 32
                val kelvin = celsius + 273.15f

                // Store the raw values without updating the UI display
                val currentTime = System.currentTimeMillis()

                // Only update UI if 10 minutes have passed or this is the first reading
                if (currentTime - lastUIUpdateTime >= 10 * 60 * 1000 || lastUIUpdateTime == 0L) {
                    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                    lastUIUpdateTime = currentTime

                    _temperatureData.update {
                        it.copy(
                            celsius = celsius,
                            fahrenheit = String.format("%.1f°F", fahrenheit),
                            kelvin = String.format("%.1f K", kelvin),
                            lastUpdated = "Last updated: ${sdf.format(Date(currentTime))}"
                        )
                    }

                    // Update the specific sensor reading in the map
                    _allSensorReadings.update { currentMap ->
                        val newMap = currentMap.toMutableMap()
                        newMap[event.sensor] = SensorReading(
                            celsius = celsius,
                            fahrenheit = fahrenheit,
                            kelvin = kelvin,
                            accuracy = SensorManager.SENSOR_STATUS_ACCURACY_LOW,
                            lastUpdated = currentTime
                        )
                        newMap
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                _temperatureData.update { it.copy(accuracy = accuracy) }
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
                delay(10 * 60 * 1000) // 10 minutes in milliseconds

                // Reset the last update time to force an update on next sensor reading
                lastUIUpdateTime = 0L

                // Log the scheduled update
                Log.d("TemperatureSensorManager", "Scheduled 10-minute update triggered")
            }
        }
    }

    fun refreshTemperature() {
        // Reset the last update time to force an update on next sensor reading
        lastUIUpdateTime = 0L

        // Show a toast to indicate refresh request
        Toast.makeText(context, "Refreshing temperature data...", Toast.LENGTH_SHORT).show()

        // Force a refresh by unregistering and re-registering the listener
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

    fun stopMonitoring() {
        sensorEventListener?.let {
            sensorManager.unregisterListener(it)
        }
        updateJob?.cancel()
        sensorEventListener = null
        updateJob = null
    }

    // Get all sensor names for debugging
    fun getAllSensorNames(): String {
        return allSensors.joinToString("\n") { it.name }
    }
}


data class TemperatureData(
    val celsius: Float = 0.0f,
    val fahrenheit: String = "--",
    val kelvin: String = "--",
    val sensorName: String = "Unknown",
    val lastUpdated: String = "Not yet updated",
    val accuracy: Int = 0
)

data class SensorReading(
    val celsius: Float,
    val fahrenheit: Float,
    val kelvin: Float,
    val accuracy: Int,
    val lastUpdated: Long
)
