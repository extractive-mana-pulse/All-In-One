@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.allinone.calendar.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.allinone.calendar.domain.model.LeadTime
import com.example.allinone.calendar.domain.model.Reminder
import com.example.allinone.calendar.presentation.vm.RemindersListViewModel
import com.example.allinone.core.presentation.R
import com.example.presentation.components.AppTopBar
import java.text.DateFormat
import java.util.Date

@Composable
fun RemindersScreen(
    onNavigateBack: () -> Unit,
    viewModel: RemindersListViewModel = hiltViewModel(),
) {
    val reminders by viewModel.reminders.collectAsStateWithLifecycle()
    val cardBackground = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
    val cardShape = RoundedCornerShape(16.dp)

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Scheduled reminders",
                onNavigationClick = onNavigateBack,
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        if (reminders.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "No reminders scheduled yet.",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 8.dp),
            ) {
                items(reminders, key = { it.id }) { reminder ->
                    ReminderRow(
                        reminder = reminder,
                        background = cardBackground,
                        shape = cardShape,
                        onCancel = { viewModel.cancel(reminder.id) },
                    )
                }
            }
        }
    }
}

@Composable
private fun ReminderRow(
    reminder: Reminder,
    background: androidx.compose.ui.graphics.Color,
    shape: RoundedCornerShape,
    onCancel: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(background)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = reminder.title,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = formatFireAt(reminder.fireAtMillis),
                style = MaterialTheme.typography.bodySmall,
            )
            if (reminder.leadTime != LeadTime.AT_TIME) {
                Text(
                    text = reminder.leadTime.displayLabel() + " before",
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
        IconButton(onClick = onCancel) {
            Icon(
                painter = painterResource(R.drawable.outline_delete_sweep_24),
                contentDescription = "Cancel reminder",
            )
        }
    }
}

private fun formatFireAt(millis: Long): String =
    DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(Date(millis))

private fun LeadTime.displayLabel(): String = when (this) {
    LeadTime.AT_TIME -> "At exact time"
    LeadTime.FIVE_MINUTES -> "5 min"
    LeadTime.TEN_MINUTES -> "10 min"
    LeadTime.THIRTY_MINUTES -> "30 min"
    LeadTime.ONE_HOUR -> "1 hour"
    LeadTime.ONE_DAY -> "1 day"
}