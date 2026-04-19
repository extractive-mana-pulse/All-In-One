@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.allinone.calendar.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import com.example.allinone.calendar.presentation.vm.NotificationSettingsViewModel
import com.example.allinone.core.presentation.R
import com.example.presentation.components.AppTopBar

@Composable
fun NotificationSettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToReminders: () -> Unit = {},
    viewModel: NotificationSettingsViewModel = hiltViewModel(),
) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    val cardBackground = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
    val cardShape = RoundedCornerShape(16.dp)

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Notifications",
                onNavigationClick = onNavigateBack,
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(cardShape)
                    .background(cardBackground)
                    .clickable(onClick = onNavigateToReminders)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Scheduled reminders",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = "See and cancel your upcoming reminders",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.outline_notifications_24),
                    contentDescription = null,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(cardShape)
                    .background(cardBackground),
            ) {
                ToggleRow(
                    title = "Local reminders",
                    subtitle = "Show notifications when a reminder fires",
                    checked = settings.notificationsEnabled,
                    onCheckedChange = viewModel::setNotificationsEnabled,
                )
                ToggleRow(
                    title = "Vibration",
                    subtitle = null,
                    checked = settings.vibrationEnabled,
                    onCheckedChange = viewModel::setVibrationEnabled,
                    enabled = settings.notificationsEnabled,
                )
                ToggleRow(
                    title = "Sound",
                    subtitle = null,
                    checked = settings.soundEnabled,
                    onCheckedChange = viewModel::setSoundEnabled,
                    enabled = settings.notificationsEnabled,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(cardShape)
                    .background(cardBackground)
                    .padding(vertical = 8.dp),
            ) {
                Text(
                    text = "Default lead time",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                )
                LeadTime.entries.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.setDefaultLeadTime(option) }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = option == settings.defaultLeadTime,
                            onClick = { viewModel.setDefaultLeadTime(option) },
                        )
                        Text(
                            text = option.label(),
                            modifier = Modifier.padding(start = 8.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ToggleRow(
    title: String,
    subtitle: String?,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            if (subtitle != null) {
                Text(subtitle, style = MaterialTheme.typography.bodySmall)
            }
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange, enabled = enabled)
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