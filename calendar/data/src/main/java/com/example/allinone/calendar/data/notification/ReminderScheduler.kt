package com.example.allinone.calendar.data.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.allinone.calendar.domain.model.Reminder

class ReminderScheduler(
    private val context: Context,
    private val alarmManager: AlarmManager,
) {

    fun schedule(reminder: Reminder) {
        val pendingIntent = pendingIntentFor(reminder.id)
        val fireAt = reminder.fireAtMillis

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
            !alarmManager.canScheduleExactAlarms()
        ) {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                fireAt,
                pendingIntent,
            )
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                fireAt,
                pendingIntent,
            )
        }
    }

    fun cancel(reminderId: Long) {
        alarmManager.cancel(pendingIntentFor(reminderId))
    }

    private fun pendingIntentFor(reminderId: Long): PendingIntent {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            action = ACTION_REMINDER_FIRED
            putExtra(EXTRA_REMINDER_ID, reminderId)
        }
        return PendingIntent.getBroadcast(
            context,
            reminderId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    companion object {
        const val ACTION_REMINDER_FIRED = "com.example.allinone.calendar.ACTION_REMINDER_FIRED"
        const val EXTRA_REMINDER_ID = "reminder_id"
    }
}