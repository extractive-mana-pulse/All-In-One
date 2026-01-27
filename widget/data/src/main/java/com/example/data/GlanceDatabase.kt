package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TemperatureDataDTO::class],
    version = 1,
    exportSchema = false
)
abstract class GlanceDatabase : RoomDatabase() {

    abstract fun tempDao(): TempDao

}