package com.example.presentation.deviceTemp.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.allinone.core.presentation.R
import com.example.domain.TemperatureData
import com.example.presentation.autoNight.vm.TemperatureViewModel

@Composable
internal fun DropdownMenuWithDetails(
    tempData: TemperatureData,
    onNavigateToHelp: () -> Unit = {},
    onNavigateToAllSensors: () -> Unit = {},
    viewModel: TemperatureViewModel = viewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    var showAboutBottomSheet by remember { mutableStateOf(false) }

    Box {
        IconButton(
            onClick = { expanded = true }
        ) {
            Icon(
                painter = painterResource(R.drawable.outline_more_vert_24),
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Show all sensors"
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.sensors_24px),
                        contentDescription = null
                    )
                },
                onClick = {
                    onNavigateToAllSensors()
                    expanded = false
                }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.refresh_temp)
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.refresh_ic),
                        contentDescription = null
                    )
                },
                onClick = {
                    viewModel.refreshTemperature()
                    expanded = false
                }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.send_feedback)
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.outline_feedback_24),
                        contentDescription = null
                    )
                },
                onClick = {
                    onNavigateToHelp()
                    expanded = false
                }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.about)
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.outline_info_24),
                        contentDescription = null
                    )
                },
                onClick = {
                    showAboutBottomSheet = true
                    expanded = false
                }
            )
        }
    }
    if (showAboutBottomSheet) {
        AboutWithSheet(
            onDismiss = { showAboutBottomSheet = false },
            tempData = tempData,
            sensorNames = viewModel.allSensorNames,
        )
    }
}