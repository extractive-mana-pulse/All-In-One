package com.example.presentation.autoNight.screens

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.core.presentation.R
import com.example.presentation.autoNight.components.SwitchOffMode
import com.example.presentation.autoNight.components.SwitchOnMode
import com.example.presentation.autoNight.vm.AutoNightViewModel
import com.example.presentation.autoNight.vm.ScheduledModeViewModel
import com.example.presentation.toastMessage
import com.google.android.gms.location.FusedLocationProviderClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduledModeScreen(
    navController: NavHostController = rememberNavController(),
    isDarkTheme: Boolean,
    onThemeChanged: (Boolean) -> Unit,
    fusedLocationClient: FusedLocationProviderClient,
    scheduleToggleState: Boolean,
) {
    val context = LocalContext.current
    var checked by rememberSaveable { mutableStateOf(false) }
    val viewModel: AutoNightViewModel = hiltViewModel()

    var latitude by remember { mutableDoubleStateOf(0.0) }
    var longitude by remember { mutableDoubleStateOf(0.0) }
    var location by remember { mutableStateOf<Location?>(null) }

    val state by viewModel.twilight.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scheduledModeViewModel: ScheduledModeViewModel = hiltViewModel()

    val locationPermissionRequired = stringResource(R.string.location_permission_required)

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
                message = locationPermissionRequired
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
                            painter = painterResource(R.drawable.outline_arrow_back_24),
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
                    checked = scheduleToggleState,
                    onCheckedChange = { status ->
                        checked = status
                        scheduledModeViewModel.toggleScheduledMode(status)
                    },
                )
            }
            HorizontalDivider()
            if (scheduleToggleState) {
                SwitchOnMode(
                    state = state,
                    isDarkTheme = isDarkTheme,
                    onThemeChanged = onThemeChanged
                )
            } else SwitchOffMode()
        }
    }
}