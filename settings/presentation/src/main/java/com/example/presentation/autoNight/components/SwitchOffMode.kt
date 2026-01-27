package com.example.presentation.autoNight.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.allinone.core.presentation.R
import com.example.presentation.autoNight.util.DialPicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// this mode is not implemented yet
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SwitchOffMode() {

    val cal = Calendar.getInstance()
    var fromTime by remember { mutableStateOf("8:00 AM") }
    var toTime by remember { mutableStateOf("8:00 PM") }
    var showDialog by remember { mutableStateOf(false) }
    var selectedTime: TimePickerState? by remember { mutableStateOf(null) }
    var selectedTimeTo: TimePickerState? by remember { mutableStateOf(null) }
    var isFromSelected by remember { mutableStateOf(true) } // Track which time is being selected
    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            DialPicker(
                onConfirm = { time ->
                    if (isFromSelected) {
                        selectedTime = time
                        fromTime = formatter.format(cal.apply {
                            set(Calendar.HOUR_OF_DAY, time.hour)
                            set(Calendar.MINUTE, time.minute)
                        }.time)
                    } else {
                        selectedTimeTo = time
                        toTime = formatter.format(cal.apply {
                            set(Calendar.HOUR_OF_DAY, time.hour)
                            set(Calendar.MINUTE, time.minute)
                        }.time)
                    }
                    showDialog = false
                },
                onDismiss = { showDialog = false },
                showDialog = showDialog
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                isFromSelected = true
                showDialog = true
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.from),
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                platformStyle = MaterialTheme.typography.bodySmall.platformStyle,
                textAlign = MaterialTheme.typography.bodySmall.textAlign,
                textDirection = MaterialTheme.typography.bodySmall.textDirection,
            )
        )
        Text(
            text = fromTime,
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                platformStyle = MaterialTheme.typography.bodySmall.platformStyle,
                textAlign = MaterialTheme.typography.bodySmall.textAlign,
                textDirection = MaterialTheme.typography.bodySmall.textDirection,
            )
        )
    }

    HorizontalDivider()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                isFromSelected = false // Set to time
                showDialog = true
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.to),
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                platformStyle = MaterialTheme.typography.bodySmall.platformStyle,
                textAlign = MaterialTheme.typography.bodySmall.textAlign,
                textDirection = MaterialTheme.typography.bodySmall.textDirection,
            )
        )
        Text(
            text = toTime,
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                platformStyle = MaterialTheme.typography.bodySmall.platformStyle,
                textAlign = MaterialTheme.typography.bodySmall.textAlign,
                textDirection = MaterialTheme.typography.bodySmall.textDirection,
            )
        )
    }
}