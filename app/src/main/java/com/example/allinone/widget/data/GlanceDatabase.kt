package com.example.allinone.widget.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.allinone.settings.deviceTemp.domain.model.TemperatureData

@Database(entities = [TemperatureData::class], version = 1)
abstract class GlanceDatabase : RoomDatabase() {

    abstract fun tempDao(): TempDao

}