package com.example.presentation.autoNight.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.presentation.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialPicker(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
    showDialog: Boolean = false
) {
    val currentTime = Calendar.getInstance()
    var showInputDialog by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    showTimePicker = showDialog

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    if (showInputDialog) {
        InputPicker(
            onConfirm = {
                showInputDialog = false
                onConfirm(timePickerState)
            },
            onDismiss = {
                showInputDialog = false
                onDismiss()
            }
        )
        return
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Enter time",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_light))
            ),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Start),
        )
        TimePicker(
            state = timePickerState,
            colors = TimePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    showTimePicker = false
                    showInputDialog = true
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_keyboard_24),
                    contentDescription = null,
                    tint = Color.Red
                )
            }
            TimePickerActionsButtons(
                onDismiss = {
                    showTimePicker = false
                    onDismiss()
                },
                onConfirm = {
                    showTimePicker = false
                    onConfirm(timePickerState)
                }
            )
        }
    }
}