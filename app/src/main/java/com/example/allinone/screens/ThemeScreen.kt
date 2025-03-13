package com.example.allinone.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.R
import com.example.allinone.core.extension.toastMessage

@Preview(showSystemUi = true, showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoNightModeScreen(
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Auto-Night Mode",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular))
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
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            AutoNightModeItem(
                context = context,
                content = "Disabled",
                toastMessageContent = "Turn off auto-night mode"
            )
            AutoNightModeItem(
                context = context,
                content = "Scheduled",
                toastMessageContent = "Schedule auto-night mode"
            )
            AutoNightModeItem(
                context = context,
                content = "Adaptive",
                toastMessageContent = "Adaptive auto-night mode"
            )
            AutoNightModeItem(
                context = context,
                content = "System Default",
                toastMessageContent = "System Default auto-night mode"
            )
        }
    }
}

@Composable
private fun AutoNightModeItem(
    context: Context,
    content: String,
    toastMessageContent: String
) {
    Text(
        text = content,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold))
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                toastMessage(
                    context = context,
                    message = toastMessageContent
                )
            }
    )
    HorizontalDivider(modifier = Modifier.padding(start = 16.dp))
}