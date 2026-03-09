package com.example.presentation.autoNight.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.allinone.core.presentation.R
import com.example.domain.model.SwipeGestureAction
import com.example.presentation.autoNight.components.SettingWithSwitch
import com.example.presentation.autoNight.components.SettingsItem
import com.example.presentation.autoNight.components.SettingsItemWithSheet
import com.example.presentation.components.AppTopBar
import com.example.presentation.swipe.SwipeActionAppearance
import com.example.presentation.swipe.components.ChatListSwipeGestureSelector
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
    var swipeAction by remember { mutableStateOf(SwipeGestureAction.DISABLE) }

    val cardBackground = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
    val cardShape = RoundedCornerShape(16.dp)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.settings),
                onNavigationClick = onNavigateBack
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            item {
                androidx.compose.foundation.layout.Column(
                    modifier = Modifier
                        .clip(cardShape)
                        .background(cardBackground)
                ) {
                    SettingsItem(
                        title = stringResource(R.string.auto_nigt_mode),
                        description = stringResource(R.string.auto_nigt_mode_desc),
                        icon = painterResource(R.drawable.outline_mode_night_24),
                        onClick = { onNavigateToNight() }
                    )
                    SettingsItemWithSheet(
                        title = stringResource(R.string.language),
                        description = stringResource(R.string.language_desc),
                        icon = painterResource(R.drawable.outline_translate_24)
                    )
                    SettingWithSwitch(
                        title = stringResource(R.string.reading_mode),
                        description = stringResource(R.string.reading_mode_desc),
                        icon = painterResource(R.drawable.outline_read_more_24),
                        checked = isReadingMode,
                        onCheckedChange = { viewModel.toggleReadingMode(it) }
                    )
                    SettingsItem(
                        title = stringResource(R.string.power_saving),
                        description = stringResource(R.string.power_saving_desc),
                        icon = painterResource(R.drawable.outline_battery_android_frame_plus_24),
                        onClick = { onNavigateToPowerSavingMode() }
                    )
                    SettingsItem(
                        title = stringResource(R.string.device_temperature),
                        description = stringResource(R.string.device_temperature_desc),
                        icon = painterResource(R.drawable.device_thermostat),
                        isLast = true,
                        onClick = { onNavigateToTemperature() }
                    )
                }
            }

            item {
                androidx.compose.foundation.layout.Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clip(cardShape)
                        .background(cardBackground)
                        .padding(16.dp)
                ) {
                    ChatListSwipeGestureSelector(
                        selectedAction = swipeAction,
                        onActionSelected = { swipeAction = it },
                        actionAppearance = { action ->
                            when (action) {
                                SwipeGestureAction.DELETE -> SwipeActionAppearance(
                                    R.drawable.outline_delete_sweep_24,
                                    Color(0xFFFF3B30)
                                )
                                SwipeGestureAction.DISABLE -> SwipeActionAppearance(
                                    R.drawable.disabled_ic,
                                    Color.LightGray
                                )
                                SwipeGestureAction.PIN -> SwipeActionAppearance(
                                    R.drawable.round_push_pin_24,
                                    Color(0xFFFFB700)
                                )
                            }
                        },
                    )
                }
            }
        }
    }
}