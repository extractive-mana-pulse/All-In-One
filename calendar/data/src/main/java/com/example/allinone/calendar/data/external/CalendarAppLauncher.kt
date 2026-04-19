package com.example.allinone.calendar.data.external

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.provider.CalendarContract
import com.example.allinone.calendar.domain.model.Reminder

data class ResolvedCalendarApp(
    val label: String,
    val packageName: String,
    val activityName: String,
    val icon: Drawable?,
)

class CalendarAppLauncher(private val context: Context) {

    fun installedCalendarApps(): List<ResolvedCalendarApp> {
        val pm = context.packageManager
        val flags = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            PackageManager.ResolveInfoFlags.of(0L)
        } else null

        val matches = if (flags != null) {
            pm.queryIntentActivities(blankInsertIntent(), flags)
        } else {
            @Suppress("DEPRECATION")
            pm.queryIntentActivities(blankInsertIntent(), 0)
        }

        return matches.map { info ->
            ResolvedCalendarApp(
                label = info.loadLabel(pm).toString(),
                packageName = info.activityInfo.packageName,
                activityName = info.activityInfo.name,
                icon = runCatching { info.loadIcon(pm) }.getOrNull(),
            )
        }.distinctBy { it.packageName }
    }

    fun launchIn(app: ResolvedCalendarApp, reminder: Reminder) {
        val intent = insertEventIntent(reminder).apply {
            component = ComponentName(app.packageName, app.activityName)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // fall back to chooser so the user can still complete the action
            context.startActivity(
                Intent.createChooser(insertEventIntent(reminder), null)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    private fun blankInsertIntent(): Intent =
        Intent(Intent.ACTION_INSERT).setData(CalendarContract.Events.CONTENT_URI)

    private fun insertEventIntent(reminder: Reminder): Intent =
        Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, reminder.title)
            reminder.description?.let {
                putExtra(CalendarContract.Events.DESCRIPTION, it)
            }
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, reminder.triggerAtMillis)
            putExtra(
                CalendarContract.EXTRA_EVENT_END_TIME,
                reminder.triggerAtMillis + DEFAULT_DURATION_MS,
            )
        }

    companion object {
        private const val DEFAULT_DURATION_MS = 30 * 60 * 1000L
    }
}
