package com.example.allinone.settings.deviceTemp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Sensors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.R
import com.example.allinone.navigation.screen.HomeScreens
import com.example.allinone.navigation.screen.SettingsScreens
import com.example.allinone.settings.TemperatureData
import com.example.allinone.settings.TemperatureSensorManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceTempScreen(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sensorManager = remember { TemperatureSensorManager(context) }
    val tempData by sensorManager.temperatureData.collectAsStateWithLifecycle()
    val sensorAvailable = remember { sensorManager.sensorAvailable }

    DisposableEffect(key1 = sensorManager) {
        sensorManager.startMonitoring(scope)
        onDispose {
            sensorManager.stopMonitoring()
        }
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.device_temperature)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    DropdownMenuWithDetails(
                        tempData = tempData,
                        navController = navController,
                        sensorManager = sensorManager
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

            TemperatureStatus(tempData = tempData)

            if (sensorAvailable) {
                IfSensorsAvailable(
                    tempData = tempData
                )
            } else {
                IfSensorsNotAvailable()
            }
        }
    }
}

@Composable
private fun IfSensorsAvailable(tempData: TemperatureData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${"%.1f".format(tempData.celsius)}°C",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_extra_bold)),
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = MaterialTheme.typography.headlineMedium.fontWeight,
                    lineHeight = MaterialTheme.typography.headlineMedium.lineHeight,
                    letterSpacing = MaterialTheme.typography.headlineMedium.letterSpacing,
                    textAlign = TextAlign.Justify
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = tempData.fahrenheit,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                        letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                        textAlign = TextAlign.Justify
                    )
                )
                Text(
                    text = tempData.kelvin,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                        letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                        textAlign = TextAlign.Justify
                    )
                )
            }
        }
    }
}

@Composable
private fun IfSensorsNotAvailable() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.no_temp_sensor_available),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular)),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight,
                    letterSpacing = MaterialTheme.typography.bodyLarge.letterSpacing,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.error
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.device_sensors_info),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular)),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                    letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                    textAlign = TextAlign.Justify
                ),
            )
        }
    }
}

@Composable
fun TemperatureStatus(tempData: TemperatureData) {
    val backgroundColor = when {
        tempData.celsius < 10 -> Color.Green
        tempData.celsius in 10.0..35.0 -> Color.Green
        tempData.celsius in 36.0..45.0 -> Color.Yellow
        else -> Color.Red
    }

    val statusText = when {
        tempData.celsius < 10 -> stringResource(R.string.cold)
        tempData.celsius in 10.0..42.0 -> stringResource(R.string.normal)
        tempData.celsius in 43.0..45.0 -> stringResource(R.string.warning)
        else -> stringResource(R.string.hot)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = Icons.Default.DeviceThermostat,
            contentDescription = null,
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(backgroundColor)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = statusText,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular)),
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = MaterialTheme.typography.headlineMedium.fontWeight,
                lineHeight = MaterialTheme.typography.headlineMedium.lineHeight,
                letterSpacing = MaterialTheme.typography.headlineMedium.letterSpacing,
            )
        )

        Text(
            text = stringResource(R.string.device_temp_sensors_desc),
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
            )
        )
    }
}

@Composable
fun DropdownMenuWithDetails(
    tempData: TemperatureData,
    navController: NavHostController,
    sensorManager: TemperatureSensorManager
) {
    var expanded by remember { mutableStateOf(false) }
    var showAboutBottomSheet by remember { mutableStateOf(false) }

    Box {
        IconButton(
            onClick = { expanded = true }
        ) {
            Icon(
                Icons.Default.MoreVert,
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
                        Icons.Outlined.Sensors,
                        contentDescription = null
                    )
                },
                onClick = {
                    navController.navigate(SettingsScreens.AllSensors.route)
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
                        Icons.Outlined.Refresh,
                        contentDescription = null
                    )
                },
                onClick = {
                    sensorManager.refreshTemperature()
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
                        Icons.Outlined.Feedback,
                        contentDescription = null
                    )
                },
                onClick = {
                    navController.navigate(HomeScreens.Help.route)
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
                        Icons.Outlined.Info,
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
            tempData = tempData
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutWithSheet(
    onDismiss: () -> Unit,
    tempData: TemperatureData
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        sheetState = sheetState,
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.about_device_temp),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold))
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = stringResource(R.string.about_device_temp_desc),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular)),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                    lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                    letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                    textAlign = TextAlign.Justify
                )
            )

            Text(
                text = "Sensor: ${tempData.sensorName}, is used to measure temperature.",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_extra_bold)),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                    lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                    letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                    textAlign = TextAlign.Justify
                )
            )
            Text(
                text = tempData.lastUpdated,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_extra_bold)),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                    lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                    letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                    textAlign = TextAlign.Justify
                )
            )

            Text(
                text = stringResource(R.string.note_device_temp_about_sheet_info),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_extra_bold)),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                    lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                    letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.error
                )
            )

            OutlinedButton(
                onClick = { onDismiss() },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.End)
            ) {
                Text(
                    text = stringResource(R.string.close)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}