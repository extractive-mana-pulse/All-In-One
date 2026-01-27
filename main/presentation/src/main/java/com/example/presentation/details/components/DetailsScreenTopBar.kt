@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.presentation.details.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.painterResource
import com.example.allinone.core.presentation.R
import com.example.domain.model.CourseDetails
import com.example.presentation.components.AppTopBar
import com.example.presentation.toastMessage

@Composable
fun DetailsScreenTopBar(
    onNavigateUp: () -> Unit,
    onNavigateAway: () -> Unit,
    courseDetails: CourseDetails,
    snackbarHostState: SnackbarHostState,
    content: @Composable (PaddingValues) -> Unit,
    onPreviewClick: (String?) -> Unit,
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
                        onClick = { onPreviewClick(courseDetails.title) }
//                                onPreviewClick = { title ->
//                            title.navigateByTitle(
//                                onNavigate = { route, shouldStopTimer ->
//                                    if (shouldStopTimer) {
//                                        onNavigateAway()
//                                    }
//                                    onNavigateByRoute(route)
//                                },
//                                onError = {
//                                    toastMessage(context = context, message = "Preview not found.")
//                                }
//                            )
//                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_preview_24),
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
                            painter = painterResource(R.drawable.outline_bookmarks_24),
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