package com.example.allinone.settings.presentation.screens

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.R
import com.example.allinone.core.extension.toastMessage
import com.example.allinone.settings.presentation.vm.ThemeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
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
    val viewModel: ThemeViewModel = hiltViewModel()
    var lat by remember { mutableDoubleStateOf(0.0) }
    var long by remember { mutableDoubleStateOf(0.0) }
    val state by viewModel.twilight.collectAsStateWithLifecycle()
    var location by remember { mutableStateOf<Location?>(null) }
    var townName by remember { mutableStateOf<String?>(null) }
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
            lat = it.latitude
            long = it.longitude
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
        viewModel.getTwilight(lat, long)
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
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
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
                    checked = isDarkTheme,
                    onCheckedChange = { onThemeChanged(!isDarkTheme) }
                    // if turned on use api to get sunset and sunrise times else use setter to set time From .... To ....
                )
            }
            Text(
                text = "Current location: ${state.results.timezone}",
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Sunrise: ${state.results.sunrise ?: "N/A"}",
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = "Sunset: ${state.results.sunset ?: "N/A"}",
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Text(
                text = "Country/Town: ${townName ?: "N/A"}",
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = "Calculating sunset and sunrise times requires a one-time check of your approximate location. Note that this location is only stored locally on your device.",
                modifier = Modifier.padding(vertical = 8.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular))
                )
            )
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