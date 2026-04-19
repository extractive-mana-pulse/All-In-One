package com.example.allinone.calendar.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.allinone.calendar.domain.model.Reminder
import com.example.allinone.calendar.domain.model.ReminderSettings

class ReminderNotifier(
    private val context: Context,
    private val notificationManager: NotificationManager,
) {

    init { ensureChannel() }

    fun show(reminder: Reminder, settings: ReminderSettings) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setContentTitle(reminder.title)
            .setContentText(reminder.description ?: DEFAULT_BODY)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        if (settings.vibrationEnabled) {
            builder.setVibrate(longArrayOf(0L, 300L, 150L, 300L))
        } else {
            builder.setVibrate(longArrayOf(0L))
        }

        if (settings.soundEnabled) {
            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        } else {
            builder.setSilent(true)
        }

        notificationManager.notify(reminder.id.toInt(), builder.build())
    }

    private fun ensureChannel() {
        val existing = notificationManager.getNotificationChannel(CHANNEL_ID)
        if (existing != null) return
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Reminders",
            NotificationManager.IMPORTANCE_HIGH,
        ).apply {
            description = "Blog and activity reminders"
            enableVibration(true)
        }
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val CHANNEL_ID = "reminders"
        private const val DEFAULT_BODY = "Time for your reminder"
    }
}

internal fun Context.notificationManagerCompat(): NotificationManager =
    ContextCompat.getSystemService(this, NotificationManager::class.java)!!
