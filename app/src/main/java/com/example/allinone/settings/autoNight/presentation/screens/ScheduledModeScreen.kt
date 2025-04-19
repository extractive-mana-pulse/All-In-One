package com.example.allinone.settings.autoNight.presentation.screens

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.R
import com.example.allinone.core.extension.toastMessage
import com.example.allinone.settings.autoNight.domain.model.Twilight
import com.example.allinone.settings.autoNight.presentation.util.DialPicker
import com.example.allinone.settings.autoNight.presentation.vm.LocationRefreshState
import com.example.allinone.settings.autoNight.presentation.vm.LocationViewModel
import com.example.allinone.settings.autoNight.presentation.vm.ThemeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduledModeScreen(
    navController: NavHostController = rememberNavController(),
    isDarkTheme: Boolean,
    onThemeChanged: (Boolean) -> Unit,
    fusedLocationClient: FusedLocationProviderClient
) {
    val context = LocalContext.current
    var checked by rememberSaveable { mutableStateOf(false) }
    val viewModel: ThemeViewModel = hiltViewModel()

    var latitude by remember { mutableDoubleStateOf(0.0) }
    var longitude by remember { mutableDoubleStateOf(0.0) }
    var location by remember { mutableStateOf<Location?>(null) }

    val state by viewModel.twilight.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { loc: Location? ->
                loc?.let {
                    latitude = it.latitude
                    longitude = it.longitude
                    location = it
                    viewModel.getTwilight(latitude, longitude)
                } ?: run {
                    toastMessage(
                        context = context,
                        message = "Not found"
                    )
                }
            }
        } else {
            toastMessage(
                context = context,
                message = context.getString(R.string.location_permission_required)
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getTwilight(latitude, longitude)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.scheduled),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold)),
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
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Navigate up from scheduled mode"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Use Local Sunset and Sunrise Times",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                        letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                        platformStyle = MaterialTheme.typography.bodyMedium.platformStyle,
                        textAlign = MaterialTheme.typography.bodyMedium.textAlign,
                        textDirection = MaterialTheme.typography.bodyMedium.textDirection,
                    )
                )
                Switch(
                    checked = checked,
                    onCheckedChange = { status ->
                        checked = status
                    },
                )
            }
            HorizontalDivider()
            when (checked) {
                true -> {
                    SwitchOnMode(state)
                }
                false -> {
                    SwitchOffMode()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwitchOffMode() {
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
            text = "From",
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
                isFromSelected = false // Set to To time
                showDialog = true
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "To",
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

@Composable
private fun SwitchOnMode(
    state: Twilight
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val locationViewModel: LocationViewModel = hiltViewModel()
    val address by locationViewModel.location
    val refreshState by locationViewModel.refreshedState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        locationViewModel.getLocation(
            context = context,
            fusedLocationClient = fusedLocationClient
        )
    }

    LaunchedEffect(refreshState) {
        when (refreshState) {
            is LocationRefreshState.Error -> {
                toastMessage(
                    context = context,
                    message = (refreshState as LocationRefreshState.Error).message
                )
            }

            LocationRefreshState.Loaded -> {
                toastMessage(
                    context = context,
                    message = "Location updated"
                )
            }

            LocationRefreshState.Loading -> {
                toastMessage(
                    context = context,
                    message = "Updating location..."
                )
            }
        }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    locationViewModel.refreshLocation(
                        context = context,
                        fusedLocationClient = fusedLocationClient
                    )
                }
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Update location",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                    letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                    platformStyle = MaterialTheme.typography.bodyMedium.platformStyle,
                    textAlign = MaterialTheme.typography.bodyMedium.textAlign,
                    textDirection = MaterialTheme.typography.bodyMedium.textDirection,
                )
            )

            Text(
                text = address,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                    letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                    platformStyle = MaterialTheme.typography.bodyMedium.platformStyle,
                    textAlign = MaterialTheme.typography.bodyMedium.textAlign,
                    textDirection = MaterialTheme.typography.bodyMedium.textDirection,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
        Text(
            text = stringResource(R.string.scheduled_switch_on_info_about_location),
            modifier = Modifier.padding(vertical = 8.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                platformStyle = MaterialTheme.typography.bodyMedium.platformStyle,
                textAlign = MaterialTheme.typography.bodyMedium.textAlign,
                textDirection = MaterialTheme.typography.bodyMedium.textDirection,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Text(
            text = "Sunrise: ${state.results.sunrise ?: "N/A"}",
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                platformStyle = MaterialTheme.typography.bodySmall.platformStyle,
                textAlign = MaterialTheme.typography.bodySmall.textAlign,
                textDirection = MaterialTheme.typography.bodySmall.textDirection,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Text(
            text = "Sunset: ${state.results.sunset ?: "N/A"}",
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                platformStyle = MaterialTheme.typography.bodySmall.platformStyle,
                textAlign = MaterialTheme.typography.bodySmall.textAlign,
                textDirection = MaterialTheme.typography.bodySmall.textDirection,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}