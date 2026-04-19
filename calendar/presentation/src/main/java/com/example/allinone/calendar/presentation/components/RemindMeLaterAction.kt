package com.example.allinone.calendar.presentation.components

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.allinone.calendar.presentation.vm.RemindMeLaterViewModel
import com.example.allinone.core.presentation.R
import com.example.presentation.helper.toastMessage

@Composable
fun RemindMeLaterAction(
    title: String,
    description: String? = null,
    sourceId: String? = null,
    viewModel: RemindMeLaterViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var open by rememberSaveable { mutableStateOf(false) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    val openSheet = remember(title, description, sourceId) {
        {
            viewModel.open(title = title, description = description, sourceId = sourceId)
            open = true
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { granted ->
        if (granted) {
            openSheet()
        } else {
            toastMessage(
                context = context,
                message = "Notification permission denied. Calendar app still available.",
            )
            openSheet()
        }
    }

    IconButton(onClick = {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
            if (granted) openSheet()
            else permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            openSheet()
        }
    }) {
        Icon(
            painter = painterResource(R.drawable.outline_notifications_24),
            contentDescription = "Remind me later",
        )
    }

    LaunchedEffect(state.isComplete) {
        if (state.isComplete) open = false
    }

    if (open) {
        RemindMeLaterSheet(
            state = state,
            onDateSelected = viewModel::onDateSelected,
            onTimeSelected = viewModel::onTimeSelected,
            onLeadTimeSelected = viewModel::onLeadTimeSelected,
            onNext = viewModel::nextStep,
            onBack = viewModel::previousStep,
            onChooseLocal = viewModel::chooseLocalReminder,
            onChooseApp = viewModel::chooseCalendarApp,
            onDismiss = { open = false },
        )
    }
}
