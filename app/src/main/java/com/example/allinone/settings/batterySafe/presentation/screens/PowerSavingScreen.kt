package com.example.allinone.settings.batterySafe.presentation.screens

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.R
import com.example.allinone.core.extension.getBatteryPercentage

// implement logic of battery icon. when battery level is 20% corresponding icon when 50% corresponding icon and so on.
// also implement logic when power saving mode is active these actions.
// 1. Dark mode activated
// 2. reduce brightness
// 3. turn off unused features: bluetooth, wi_fi and so on
// 4. limit notifications if they exist locally.

@Preview(showSystemUi = true, showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatterySavingScreen(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                LargeTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.battery_saving),
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
                        IconButton(
                            onClick = {
                                navController.navigateUp()
                            }
                        ) {
                            Icon(
                                Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(R.string.from_power_saving_to_somewhere)
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LaunchedEffect(context.getBatteryPercentage()) {
                if (context.getBatteryPercentage() != null && context.getBatteryPercentage()!! < sliderPosition) {
                    // activate dark theme
                    // reduce brightness to 20%
                    // turn off unused features: bluetooth, wi_fi and etc.
                    // limit notifications if they exist locally.
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.off),
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
                    text = "When below ${sliderPosition.toInt()}%.",
                    textAlign = TextAlign.Center,
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
                    text = stringResource(R.string.on),
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
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                valueRange = 0f..100f,
            )
            Text(
                text = "Automatically reduce power usage and animations when your battery is below ${sliderPosition.toInt()}%",
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
                text = "Currently battery level is: ${context.getBatteryPercentage()?.toInt()}%",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                    letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                    platformStyle = MaterialTheme.typography.bodyMedium.platformStyle,
                    textAlign = MaterialTheme.typography.bodyMedium.textAlign,
                    textDirection = MaterialTheme.typography.bodyMedium.textDirection,
                ),
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}