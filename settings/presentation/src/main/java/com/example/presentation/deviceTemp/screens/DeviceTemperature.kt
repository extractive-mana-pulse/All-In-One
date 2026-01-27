package com.example.presentation.deviceTemp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.allinone.core.presentation.R
import com.example.presentation.autoNight.vm.TemperatureViewModel
import com.example.presentation.deviceTemp.components.DropdownMenuWithDetails
import com.example.presentation.deviceTemp.components.IfSensorsAvailable
import com.example.presentation.deviceTemp.components.IfSensorsNotAvailable
import com.example.presentation.deviceTemp.components.TemperatureStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceTempScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHelp: () -> Unit,
    onNavigateToAllSensors: () -> Unit,
) {
    val viewModel: TemperatureViewModel = hiltViewModel()
    val sensorAvailable = viewModel.sensorAvailable
    val temperatureData = viewModel.temperatureData.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.device_temperature),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular)),
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            letterSpacing = MaterialTheme.typography.titleLarge.letterSpacing,
                            lineHeight = MaterialTheme.typography.titleLarge.lineHeight,
                            platformStyle = MaterialTheme.typography.titleLarge.platformStyle,
                            textAlign = MaterialTheme.typography.titleLarge.textAlign,
                            textDirection = MaterialTheme.typography.titleLarge.textDirection,
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            painter = painterResource(R.drawable.outline_arrow_back_24),
                            contentDescription = stringResource(R.string.device_temperature_to_somewhere)
                        )
                    }
                },
                actions = {
                    DropdownMenuWithDetails(
                        tempData = temperatureData.value,
                        onNavigateToHelp = onNavigateToHelp,
                        onNavigateToAllSensors = onNavigateToAllSensors,
                        viewModel = viewModel
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            TemperatureStatus(tempData = temperatureData.value)

            if (sensorAvailable) IfSensorsAvailable(tempData = temperatureData.value) else IfSensorsNotAvailable()
        }
    }
}