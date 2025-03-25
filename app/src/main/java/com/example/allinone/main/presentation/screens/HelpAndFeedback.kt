package com.example.allinone.main.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.core.extension.toastMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpAndFeedbackScreen(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Help and Feedback")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Navigate up from Help and Feedback screen"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Popular help resources",
                modifier = Modifier.align(Alignment.Start)
            )
            ListItem(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable {
                        toastMessage(
                            context = context,
                            message = "Hello world"
                        )
                    },
                headlineContent = {
                    Text(text = "Contact Support")
                },
                supportingContent = {
                    Text(text = "Get in touch with our support team")
                },
//                trailingContent = {
//                    Text(text = "Help")
//                },
                leadingContent = {
                    Icon(
                        Icons.AutoMirrored.Outlined.Article,
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.outlineVariant)
                            .padding(8.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
            )
            HorizontalDivider()
            ListItem(
                headlineContent = {
                    Text(text = "Send feedback")
                },
                leadingContent = {
                    Icon(
                        Icons.Default.Feedback,
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.outlineVariant)
                            .padding(8.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                modifier = Modifier.clickable {
                    toastMessage(
                        context = context,
                        message = "Hello world"
                    )
                    // open bottom bar that works directly with gmail.
                }
            )
        }
    }
}