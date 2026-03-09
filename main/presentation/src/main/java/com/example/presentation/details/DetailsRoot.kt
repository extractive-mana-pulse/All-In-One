package com.example.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.details.components.DetailsItem
import com.example.presentation.details.components.DetailsScreenTopBar
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsScreen(
    id: Int,
    onNavigateUp: () -> Unit = {},
    shouldShowReadingModeSnackbar: Boolean,
    navigateToCodeLab: (String) -> Unit = {},
    onAction: (DetailScreenAction) -> Unit = {},
) {
    val verticalScroll = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val detailsViewModel : DetailsViewModel = hiltViewModel()
    val courseDetails by detailsViewModel.courseDetails.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) { detailsViewModel.loadCourse(id) }

    LaunchedEffect(courseDetails) {
        onAction(DetailScreenAction.OnCourseLoaded(id))
    }

    LaunchedEffect(shouldShowReadingModeSnackbar) {
        if (shouldShowReadingModeSnackbar) {
            onAction(DetailScreenAction.OnSnackbarShown)

            val result = snackbarHostState.showSnackbar(
                message = "Would you like to turn on reading mode?",
                actionLabel = "Turn On",
                duration = SnackbarDuration.Indefinite,
                withDismissAction = true
            )

            when(result) {
                SnackbarResult.Dismissed -> onAction(DetailScreenAction.OnSnackbarDismiss)
                SnackbarResult.ActionPerformed -> onAction(DetailScreenAction.OnSnackbarActionPerformed(enabled = true))
            }
        }
    }

    courseDetails?.let { courseDetails ->
        DetailsScreenTopBar(
            courseDetails = courseDetails,
            snackbarHostState = snackbarHostState,
            onNavigateUp = { onNavigateUp() },
            onNavigateAway = { onAction(DetailScreenAction.OnNavigateAway) },
            onPreviewClick = { courseTitle -> navigateToCodeLab(courseTitle ?: "") },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(state = verticalScroll)
                        .padding(horizontal = 16.dp)
                ) {
                    when (courseDetails) {
                        else -> {
                            DetailsItem(course = courseDetails)
                        }
                    }
                }
            }
        )
    }
}