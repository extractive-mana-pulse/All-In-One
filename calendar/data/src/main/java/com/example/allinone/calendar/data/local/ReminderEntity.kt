package com.example.allinone.calendar.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.allinone.calendar.domain.model.LeadTime
import com.example.allinone.calendar.domain.model.Reminder

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val description: String?,
    val triggerAtMillis: Long,
    val leadTimeMinutes: Int,
    val sourceId: String?,
) {
    fun toDomain(): Reminder = Reminder(
        id = id,
        title = title,
        description = description,
        triggerAtMillis = triggerAtMillis,
        leadTime = LeadTime.fromMinutes(leadTimeMinutes),
        sourceId = sourceId,
    )

    companion object {
        fun fromDomain(reminder: Reminder): ReminderEntity = ReminderEntity(
            id = reminder.id,
            title = reminder.title,
            description = reminder.description,
            triggerAtMillis = reminder.triggerAtMillis,
            leadTimeMinutes = reminder.leadTime.minutes,
            sourceId = reminder.sourceId,
        )
    }
}