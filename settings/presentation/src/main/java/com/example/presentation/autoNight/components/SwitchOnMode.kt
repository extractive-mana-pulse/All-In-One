package com.example.presentation.autoNight.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.allinone.core.presentation.R
import com.example.domain.model.LocationResult
import com.example.domain.model.Twilight
import com.example.presentation.autoNight.vm.LocationViewModel
import com.example.presentation.toastMessage
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
internal fun SwitchOnMode(
    state: Twilight,
    isDarkTheme: Boolean,
    onThemeChanged: (Boolean) -> Unit,
    viewModel: LocationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val locationState by viewModel.locationState.collectAsStateWithLifecycle()

    // Auto-adjust theme based on sunrise/sunset
    AutoAdjustTheme(
        state = state,
        isDarkTheme = isDarkTheme,
        onThemeChanged = onThemeChanged
    )

    // Show toast messages based on location state
    LaunchedEffect(locationState) {
        when (locationState) {
            is LocationResult.Loading -> {
                toastMessage(context, "Updating location...")
            }
            is LocationResult.Success -> {
                toastMessage(context, "Location updated")
            }
            is LocationResult.Error -> {
                toastMessage(context, (locationState as LocationResult.Error).message)
            }
        }
    }

    Column {
        LocationRow(
            locationState = locationState,
            onRefreshClick = { viewModel.refreshLocation() }
        )

        InfoText(
            text = stringResource(R.string.scheduled_switch_on_info_about_location)
        )

        SunriseSunsetInfo(state = state)
    }
}

@Composable
private fun AutoAdjustTheme(
    state: Twilight,
    isDarkTheme: Boolean,
    onThemeChanged: (Boolean) -> Unit
) {
    val currentLocalTime = LocalDateTime.now().toLocalTime()
    val sunriseString = state.results.sunrise
    val sunsetString = state.results.sunset

    if (sunriseString != null && sunsetString != null) {
        val sunriseTime = LocalTime.parse(sunriseString)
        val sunsetTime = LocalTime.parse(sunsetString)

        val isDayTime = currentLocalTime.isAfter(sunriseTime) &&
                currentLocalTime.isBefore(sunsetTime)

        when {
            isDayTime && isDarkTheme -> onThemeChanged(false)
            !isDayTime && !isDarkTheme -> onThemeChanged(true)
        }
    }
}

@Composable
private fun LocationRow(
    locationState: LocationResult,
    onRefreshClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRefreshClick() }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.update_location),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium))
            )
        )

        when (locationState) {
            is LocationResult.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(8.dp)
                )
            }
            is LocationResult.Success -> {
                Text(
                    text = locationState.address,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
            is LocationResult.Error -> {
                Text(
                    text = "Tap to retry",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                        color = MaterialTheme.colorScheme.error
                    )
                )
            }
        }
    }
}

@Composable
private fun InfoText(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(vertical = 8.dp),
        style = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}

@Composable
private fun SunriseSunsetInfo(state: Twilight) {
    Column {
        SunTimeText(
            label = "Sunrise",
            time = state.results.sunrise
        )
        SunTimeText(
            label = "Sunset",
            time = state.results.sunset
        )
    }
}

@Composable
private fun SunTimeText(
    label: String,
    time: String?
) {
    Text(
        text = "$label: ${time ?: "N/A"}",
        style = MaterialTheme.typography.bodySmall.copy(
            fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}