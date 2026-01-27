package com.example.presentation.autoNight.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.core.presentation.R
import com.example.presentation.autoNight.components.AutoNightModeItem
import com.example.presentation.autoNight.vm.AutoNightViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoNightModeScreen(
    navController: NavHostController = rememberNavController(),
    onThemeChanged: (Boolean) -> Unit,
    onNavigateToScheduleMode: () -> Unit,
    onNavigateToAdaptiveMode: () -> Unit,

) {
    val viewModel: AutoNightViewModel = hiltViewModel()
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
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular))
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            val modes = listOf(
                stringResource(R.string.disabled),
                stringResource(R.string.scheduled),
                stringResource(R.string.adaptive),
                stringResource(R.string.default_mode)
            )

            val disabledText = stringResource(R.string.disabled)
            val scheduledText = stringResource(R.string.scheduled)
            val adaptiveText = stringResource(R.string.adaptive)
            val defaultModeText = stringResource(R.string.default_mode)

            modes.forEach { mode ->
                AutoNightModeItem(
                    content = mode,
                    isSelected = selectedMode == mode,
                    onClick = {
                        viewModel.selectMode(mode)
                        when (mode) {
                            // Disabled mode: theme always light
                            disabledText -> onThemeChanged(false)

                            // Scheduled mode: time-based theme switching
                            scheduledText -> onNavigateToScheduleMode()

                            // Adaptive mode: brightness-based theme switching
                            adaptiveText -> onNavigateToAdaptiveMode()

                            // System default mode: follows system theme
                            defaultModeText -> Unit
                        }
                    }
                )
            }
        }
    }
}

