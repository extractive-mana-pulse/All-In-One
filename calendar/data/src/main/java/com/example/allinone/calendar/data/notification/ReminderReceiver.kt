package com.example.allinone.calendar.data.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.allinone.calendar.domain.repository.ReminderRepository
import com.example.allinone.calendar.domain.repository.ReminderSettingsRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ReminderReceiver : BroadcastReceiver() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface ReminderReceiverEntryPoint {
        fun reminderRepository(): ReminderRepository
        fun settingsRepository(): ReminderSettingsRepository
        fun notifier(): ReminderNotifier
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != ReminderScheduler.ACTION_REMINDER_FIRED) return
        val id = intent.getLongExtra(ReminderScheduler.EXTRA_REMINDER_ID, -1L)
        if (id == -1L) return

        val entryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            ReminderReceiverEntryPoint::class.java,
        )
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.Default + SupervisorJob()).launch {
            try {
                val reminder = entryPoint.reminderRepository().get(id) ?: return@launch
                val settings = entryPoint.settingsRepository().settings.first()
                if (!settings.notificationsEnabled) return@launch
                entryPoint.notifier().show(reminder, settings)
            } finally {
                pendingResult.finish()
            }
        }
    }
}