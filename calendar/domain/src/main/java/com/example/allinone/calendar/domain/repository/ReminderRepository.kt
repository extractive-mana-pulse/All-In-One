package com.example.allinone.calendar.domain.repository

import com.example.allinone.calendar.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    fun observeAll(): Flow<List<Reminder>>
    suspend fun get(id: Long): Reminder?
    suspend fun upsert(reminder: Reminder): Long
    suspend fun delete(id: Long)
}