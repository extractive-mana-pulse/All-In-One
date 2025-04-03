package com.example.allinone.settings.presentation.screens

import android.content.Context
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.core.extension.toastMessage
import com.example.allinone.settings.presentation.vm.SliderViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveModeScreen(
    navController: NavHostController = rememberNavController(),
    isDarkTheme: Boolean,
    onThemeChanged: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val viewModel: SliderViewModel = hiltViewModel()
    val sliderValue by viewModel.sliderValue.collectAsState(initial = 0.5f)
    var startBehaveBrightness by remember { mutableStateOf(sliderValue) }
    var currentBrightness by remember { mutableFloatStateOf(getBrightness(context)) }
    val shouldBeDarkTheme = currentBrightness >= startBehaveBrightness

    LaunchedEffect(sliderValue) {
        startBehaveBrightness = sliderValue // Update on start
    }

    LaunchedEffect(currentBrightness, startBehaveBrightness) {
        if (shouldBeDarkTheme != isDarkTheme) onThemeChanged(shouldBeDarkTheme)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adaptive Mode (Beta)") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Navigate up")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        currentBrightness = getBrightness(context)
                        toastMessage(context, "Brightness updated. Current brightness: ${currentBrightness.roundToInt()}%")
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh current brightness")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Current Brightness: ${currentBrightness.roundToInt()}%",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(32.dp))

            Slider(
                value = startBehaveBrightness,
                onValueChange = { newValue ->
                    startBehaveBrightness = newValue
                },
                onValueChangeFinished = {
                    viewModel.updateSliderValue(startBehaveBrightness) // Save the new slider value
                },
                valueRange = 1f..33f
            )

            Text(
                text = "Switch to dark mode when brightness falls below ${(startBehaveBrightness.roundToInt())}%",
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}

private fun getBrightness(context: Context): Float {
    return try {
        val brightness = Settings.System.getInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS
        )

        val normalized = 1f + (brightness * 32f) / 255f
        normalized
    } catch (e: Exception) {
        toastMessage(
            context = context,
            message = "Error getting brightness: ${e.message}"
        )
        17f
    }
}