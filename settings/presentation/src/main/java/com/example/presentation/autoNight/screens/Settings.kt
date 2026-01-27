package com.example.presentation.autoNight.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.allinone.core.presentation.R
import com.example.presentation.autoNight.components.SettingWithSwitch
import com.example.presentation.autoNight.components.SettingsItem
import com.example.presentation.autoNight.components.SettingsItemWithSheet
import com.example.presentation.vm.ReadingModeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    isReadingMode: Boolean,
    onNavigateBack: () -> Unit,
    onNavigateToNight: () -> Unit,
    onNavigateToPowerSavingMode: () -> Unit,
    onNavigateToTemperature: () -> Unit
) {
    val viewModel: ReadingModeViewModel = hiltViewModel()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings),
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
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            painter = painterResource(R.drawable.outline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            /** Auto-Night Mode */
            item {
                SettingsItem(
                    title = stringResource(R.string.auto_nigt_mode),
                    description = stringResource(R.string.auto_nigt_mode_desc),
                    icon = painterResource(R.drawable.outline_mode_night_24),
                    onClick = { onNavigateToNight() }
                )
            }

            /** Language */
            item {
                SettingsItemWithSheet(
                    title = stringResource(R.string.language),
                    description = stringResource(R.string.language_desc),
                    icon = painterResource(R.drawable.outline_translate_24)
                )
            }

            /** Reading Mode */
            item {
                SettingWithSwitch(
                    title = stringResource(R.string.reading_mode),
                    description = stringResource(R.string.reading_mode_desc),
                    icon = painterResource(R.drawable.outline_read_more_24),
                    checked = isReadingMode,
                    onCheckedChange = { viewModel.toggleReadingMode(it) }
                )
            }

            /** Power Saving Mode */
            item {
                SettingsItem(
                    title = stringResource(R.string.power_saving),
                    description = stringResource(R.string.power_saving_desc),
                    icon = painterResource(R.drawable.outline_battery_android_frame_plus_24),
                    onClick = {
                        onNavigateToPowerSavingMode()
                    }
                )
            }

            /** Device temperature */
            item {
                SettingsItem(
                    title = stringResource(R.string.device_temperature),
                    description = stringResource(R.string.device_temperature_desc),
                    icon = painterResource(R.drawable.device_thermostat),
                    onClick = {
                        onNavigateToTemperature()
                    }
                )
            }
        }
    }
}