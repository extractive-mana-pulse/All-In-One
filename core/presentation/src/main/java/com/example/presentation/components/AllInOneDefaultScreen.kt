package com.example.presentation.components

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun AllInOneDefaultScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    containerColor: Color = MaterialTheme.colorScheme.background,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
) {

    Scaffold(
        modifier = modifier,
        containerColor = containerColor,
        bottomBar = {
            bottomBar()
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    Snackbar(
                        snackbarData = data,
                        shape = RoundedCornerShape(8.dp),
                    )
                }
            )
        },
    ) { innerPadding ->
        val view = LocalView.current

        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            content()
        }

        SideEffect {
            val window = (view.context as? Activity)?.window
            if (!view.isInEditMode && window != null) {
                WindowInsetsControllerCompat(window, window.decorView)
                    .isAppearanceLightStatusBars = true
            }
        }
    }
}