package com.example.presentation.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.vm.TimerViewModel

@Composable
fun DetailsRoot(
    id: Int,
    onNavigateUp: () -> Unit = {},
    navigateToCodeLabWithRoute: (String) -> Unit = {},
    timerViewModel: TimerViewModel = hiltViewModel()
) {
    val shouldShowSnackbar by timerViewModel.shouldShowReadingModeSnackbar.collectAsStateWithLifecycle()

    DetailsScreen(
        id = id,
        onAction = timerViewModel::onAction,
        onNavigateUp = { onNavigateUp() },
        shouldShowReadingModeSnackbar = shouldShowSnackbar,
        navigateToCodeLab = { route -> navigateToCodeLabWithRoute(route) }
    )
}