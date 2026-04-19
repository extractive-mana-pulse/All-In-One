package com.example.allinone.calendar.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import com.example.allinone.calendar.data.external.ResolvedCalendarApp
import com.example.allinone.calendar.domain.model.LeadTime
import com.example.allinone.core.presentation.R

@Composable
internal fun LeadStep(
    selected: LeadTime,
    onSelected: (LeadTime) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Notify me",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp),
        )
        LeadTime.entries.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelected(option) }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(selected = option == selected, onClick = { onSelected(option) })
                Text(text = option.label(), modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
internal fun DestinationStep(
    apps: List<ResolvedCalendarApp>,
    onLocal: () -> Unit,
    onApp: (ResolvedCalendarApp) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Where should we remind you?",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onLocal() }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.outline_notifications_24),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
            )
            Column {
                Text("Local reminder only", style = MaterialTheme.typography.bodyLarge)
                Text(
                    "We'll send you a notification",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }

        if (apps.isNotEmpty()) {
            HorizontalDivider()
            Text(
                text = "Save to calendar app",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(vertical = 8.dp),
            )
            apps.forEach { app ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onApp(app) }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    val icon = app.icon
                    if (icon != null) {
                        val width = icon.intrinsicWidth.coerceAtLeast(1)
                        val height = icon.intrinsicHeight.coerceAtLeast(1)
                        val bitmap = createBitmap(width, height)
                        val canvas = android.graphics.Canvas(bitmap)
                        icon.setBounds(0, 0, width, height)
                        icon.draw(canvas)
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                        )
                    }
                    Column {
                        Text(app.label, style = MaterialTheme.typography.bodyLarge)
                        Text(app.packageName, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

private fun LeadTime.label(): String = when (this) {
    LeadTime.AT_TIME -> "At the exact time"
    LeadTime.FIVE_MINUTES -> "5 minutes before"
    LeadTime.TEN_MINUTES -> "10 minutes before"
    LeadTime.THIRTY_MINUTES -> "30 minutes before"
    LeadTime.ONE_HOUR -> "1 hour before"
    LeadTime.ONE_DAY -> "1 day before"
}
