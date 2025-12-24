@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.presentation.details.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import com.example.allinone.core.components.AppTopBar
import com.example.allinone.core.extension.toastMessage
import com.example.allinone.main.domain.model.CourseDetails
import com.example.allinone.navigation.util.navigateByTitle

@Composable
fun DetailsScreenTopBar(
    onNavigateUp: () -> Unit,
    onNavigateAway: () -> Unit,
    onNavigateByRoute: (String) -> Unit,
    courseDetails: CourseDetails,
    snackbarHostState: SnackbarHostState,
    content: @Composable (PaddingValues) -> Unit,
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AppTopBar(
                title = courseDetails.title ?: "Blog",
                onNavigationClick = {
                    onNavigateAway()
                    onNavigateUp()
                },
                onActionClick = {
                    IconButton(
                        onClick = {
                            courseDetails.title?.navigateByTitle(
                                onNavigate = { route, shouldStopTimer ->
                                    if (shouldStopTimer) {
                                        onNavigateAway()
                                    }
                                    onNavigateByRoute(route)
                                },
                                onError = {
                                    toastMessage(context = context, message = "Preview not found.")
                                }
                            )
                        }
                    ) {
                        Icon(
                            Icons.Default.Preview,
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = {
                            toastMessage(
                                context = context,
                                message = "In progress. This feature will be implemented soon"
                            )
                        }
                    ) {
                        Icon(
                            Icons.Outlined.BookmarkBorder,
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
        ) { innerPadding ->
        content(innerPadding)
    }
}