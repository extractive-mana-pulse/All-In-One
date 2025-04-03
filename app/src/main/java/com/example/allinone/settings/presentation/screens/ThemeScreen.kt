package com.example.allinone.settings.presentation.screens

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.R
import com.example.allinone.core.extension.isInDarkMode
import com.example.allinone.navigation.screen.SettingsScreens
import com.example.allinone.settings.presentation.vm.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoNightModeScreen(
    navController: NavHostController = rememberNavController(),
    isDarkTheme: Boolean,
    onThemeChanged: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val viewModel: ThemeViewModel = hiltViewModel()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val selectedMode by viewModel.selectedMode.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.auto_nigt_mode),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular)),
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            letterSpacing = MaterialTheme.typography.titleLarge.letterSpacing,
                            lineHeight = MaterialTheme.typography.titleLarge.lineHeight
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
                            contentDescription = null
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            val modes = listOf(
                stringResource(R.string.disabled),
                stringResource(R.string.scheduled),
                stringResource(R.string.adaptive),
                stringResource(R.string.default_mode)
            )

            modes.forEach { mode ->
                AutoNightModeItem(
                    content = mode,
                    isSelected = selectedMode == mode,
                    onClick = {
                        viewModel.selectItem(mode)
                        when (mode) {
                            /** Disabled mode is mode where theme always light.*/
                            context.getString(R.string.disabled) -> onThemeChanged(false)

                            /** Scheduled mode has 2 options.
                             * 1. When user can select manually a time range
                             * 2. User via geolocation can identify location and identify twilight, via these time range set mode.*/
                            context.getString(R.string.scheduled) -> {
                                navController.navigate(SettingsScreens.ScheduledMode.route)
                            }

                            /** Adaptive mode is mode where a brightness goes below set threshold, then theme changes to dark.*/
                            context.getString(R.string.adaptive) -> {
                                navController.navigate(SettingsScreens.AdaptiveMode.route)
                            }

                            /** This mode is system default mode. where depending on system mode apps mode going to be defined. */
                            context.getString(R.string.default_mode) -> {
                                val isDarkMode = context.isInDarkMode()
                                onThemeChanged(isDarkMode)
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun AutoNightModeItem(
    content: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val viewModel : ThemeViewModel = hiltViewModel()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                viewModel.selectItem(content)
                onClick()
            }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold))
            )
        )
        if (isSelected) {
            Icon(
                Icons.Default.Check,
                contentDescription = null
            )
        }
    }
    HorizontalDivider(modifier = Modifier.padding(start = 16.dp))
}