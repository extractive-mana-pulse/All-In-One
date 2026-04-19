@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.allinone.calendar.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.allinone.calendar.data.external.ResolvedCalendarApp
import com.example.allinone.calendar.domain.model.LeadTime
import com.example.allinone.calendar.presentation.vm.RemindMeLaterUiState

@Composable
fun RemindMeLaterSheet(
    state: RemindMeLaterUiState,
    onDateSelected: (Long?) -> Unit,
    onTimeSelected: (Int, Int) -> Unit,
    onLeadTimeSelected: (LeadTime) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onChooseLocal: () -> Unit,
    onChooseApp: (ResolvedCalendarApp) -> Unit,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            when (state.step) {
                RemindMeLaterUiState.Step.DATE -> DateStep(
                    selectedMillis = state.selectedDateMillis,
                    onDateSelected = onDateSelected,
                )
                RemindMeLaterUiState.Step.TIME -> TimeStep(
                    initialHour = state.selectedHour,
                    initialMinute = state.selectedMinute,
                    onTimeSelected = onTimeSelected,
                )
                RemindMeLaterUiState.Step.LEAD -> LeadStep(
                    selected = state.leadTime,
                    onSelected = onLeadTimeSelected,
                )
                RemindMeLaterUiState.Step.DESTINATION -> DestinationStep(
                    apps = state.calendarApps,
                    onLocal = onChooseLocal,
                    onApp = onChooseApp,
                )
            }

            if (state.step != RemindMeLaterUiState.Step.DESTINATION) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                ) {
                    if (state.step != RemindMeLaterUiState.Step.DATE) {
                        TextButton(onClick = onBack) { Text("Back") }
                    }
                    val enabled = when (state.step) {
                        RemindMeLaterUiState.Step.DATE -> state.canProceedFromDate
                        else -> true
                    }
                    Button(onClick = onNext, enabled = enabled) { Text("Proceed") }
                }
            }
        }
    }
}

@Composable
private fun DateStep(
    selectedMillis: Long?,
    onDateSelected: (Long?) -> Unit,
) {
    val pickerState = rememberDatePickerState(initialSelectedDateMillis = selectedMillis)
    LaunchedEffect(pickerState.selectedDateMillis) {
        onDateSelected(pickerState.selectedDateMillis)
    }
    DatePicker(state = pickerState)
}

@Composable
private fun TimeStep(
    initialHour: Int,
    initialMinute: Int,
    onTimeSelected: (Int, Int) -> Unit,
) {
    val pickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true,
    )
    LaunchedEffect(pickerState.hour, pickerState.minute) {
        onTimeSelected(pickerState.hour, pickerState.minute)
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TimePicker(state = pickerState)
    }
}
