package com.example.allinone.settings.presentation.screens

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.allinone.settings.domain.model.Twilight
import com.example.allinone.settings.presentation.vm.ThemeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
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
    val scope = rememberCoroutineScope()
    val viewModel: ThemeViewModel = hiltViewModel()
    var latitude by remember { mutableDoubleStateOf(0.0) }
    var longitude by remember { mutableDoubleStateOf(0.0) }
    var townName by remember { mutableStateOf<String?>(null) }
    var location by remember { mutableStateOf<Location?>(null) }
    val state by viewModel.twilight.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            toastMessage(
                context = context,
                message = context.getString(R.string.location_permission_required)
            )
        } else {
            getCurrentLocation(fusedLocationClient) { loc ->
                location = loc
            }
        }
        location?.let {
            latitude = it.latitude
            longitude = it.longitude
            getTownName(
                townName = townName ?: "Tashkent",
                location = it,
                context = context
            )
        }
    }

    LaunchedEffect(Unit) {
        // viewModel.getTwilight(it.latitude, it.longitude)
        // tashkent lat -> 41.311081 & long -> 69.240562
        viewModel.getTwilight(latitude, longitude)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.scheduled),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular))
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,

                ),
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Use Local Sunset and Sunrise Times",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold))
                    )
                )

                Switch(
                    checked = checked,
                    onCheckedChange = { status ->
                        checked = status
                    },
                )
            }
            if (checked)
                SwitchOnMode(state, townName)
            else
                SwitchOffMode()
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
                isFromSelected = true // Set to From time
                showDialog = true
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "From",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular))
            )
        )
        Text(
            text = fromTime,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular))
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
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular))
            )
        )
        Text(
            text = toTime,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular))
            )
        )
    }
}

@Composable
private fun SwitchOnMode(
    state: Twilight,
    townName: String?
) {
    Text(
        text = "Current location: ${state.results.timezone}",
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular))
        )
    )
    Text(
        text = "Sunrise: ${state.results.sunrise ?: "N/A"}",
        modifier = Modifier.padding(horizontal = 8.dp),
        style = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular))
        )
    )
    Text(
        text = "Sunset: ${state.results.sunset ?: "N/A"}",
        modifier = Modifier.padding(horizontal = 8.dp),
        style = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular))
        )
    )

    Text(
        text = "Country/Town: ${townName ?: "N/A"}",
        modifier = Modifier.padding(horizontal = 8.dp),
        style = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular))
        )
    )
    Text(
        text = "Calculating sunset and sunrise times requires a one-time check of your approximate location. Note that this location is only stored locally on your device.",
        modifier = Modifier.padding(vertical = 8.dp),
        style = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular))
        )
    )
}

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
                    Icons.Outlined.Keyboard,
                    contentDescription = null
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputPicker(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

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
            text = "Select time",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_light))
            ),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Start),
        )
        TimeInput(
            state = timePickerState,
        )
        TimePickerActionsButtons(
            onDismiss = {
                onDismiss()
            },
            onConfirm = {
                onConfirm(timePickerState)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerActionsButtons(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            onClick = onDismiss
        ) {
            Text("Cancel")
        }
        TextButton(
            onClick = onConfirm
        ) {
            Text("Ok")
        }
    }
}

@SuppressLint("MissingPermission")
private fun getCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onSuccess: (Location?) -> Unit
) {
    val task: Task<Location> = fusedLocationClient.lastLocation
    task.addOnSuccessListener { location: Location? ->
        onSuccess(location)
    }
}

private fun getTownName(
    townName: String?,
    location: Location,
    context: Context
) {
    var mutableTownName = townName
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        if (addresses?.isNotEmpty() == true) {
            val address = addresses[0]
            mutableTownName = address.locality
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}