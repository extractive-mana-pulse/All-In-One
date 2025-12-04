package com.example.allinone.main.presentation.screens

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.allinone.R
import com.example.allinone.core.extension.toastMessage
import com.example.allinone.core.util.ui.CustomAlertDialog
import com.example.allinone.main.domain.model.CourseDetails
import com.example.allinone.main.presentation.vm.DetailsViewModel
import com.example.allinone.main.presentation.vm.TimerViewModel
import com.example.allinone.navigation.screen.Screens
import com.example.allinone.settings.readingMode.presentation.vm.ReadingModeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavHostController = rememberNavController(),
    id: Int,
    timerViewModel: TimerViewModel
) {

    val detailsViewModel : DetailsViewModel = hiltViewModel()
    val context = LocalContext.current
    val verticalScroll = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val readingViewModel : ReadingModeViewModel = hiltViewModel()
    val timerValue by timerViewModel.timer.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val courseDetails by detailsViewModel.courseDetails.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) { detailsViewModel.loadCourse(id) }

    LaunchedEffect(courseDetails) {
        if (courseDetails != null) {
            timerViewModel.startTimer()
        }
    }

    LaunchedEffect(timerValue) {
        if (timerViewModel.readingModeSnackbar(15)) {
            val result = snackbarHostState.showSnackbar(
                message = "Would you like to turn on reading mode? ",
                actionLabel = "Turn On",
                duration = SnackbarDuration.Indefinite,
                withDismissAction = true
            )
            when(result) {
                SnackbarResult.Dismissed -> timerViewModel.stopTimer()
                SnackbarResult.ActionPerformed -> readingViewModel.toggleReadingMode(true)
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = courseDetails?.title ?: "Blog",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold)),
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            letterSpacing = MaterialTheme.typography.titleLarge.letterSpacing,
                            lineHeight = MaterialTheme.typography.titleLarge.lineHeight,
                            platformStyle = MaterialTheme.typography.titleLarge.platformStyle,
                            textAlign = MaterialTheme.typography.titleLarge.textAlign,
                            textDirection = MaterialTheme.typography.titleLarge.textDirection,
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            timerViewModel.stopTimer()
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(R.string.details_screen_to_somewhere)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            when (courseDetails?.title) {
                                "Compose article" -> {
                                    navController.navigate(Screens.ComposeArticleScreen.route)
                                }
                                "Quadrant" -> {
                                    navController.navigate(Screens.Quadrant.route)
                                }
                                "Tip Calculator" -> {
                                    navController.navigate(Screens.TipCalculator.route)
                                }
                                "Art Space App" -> {
                                    navController.navigate(Screens.ArtSpace.route)
                                    timerViewModel.stopTimer()
                                }
                                "Lemonade" -> {
                                    navController.navigate(Screens.Lemonade.route)
                                    timerViewModel.stopTimer()
                                }
                                "Task Manager" -> {
                                    navController.navigate(Screens.TaskManagerScreen.route)
                                    timerViewModel.stopTimer()
                                }
                                "Art Space" -> {
                                    navController.navigate(Screens.ArtSpace.route)
                                    timerViewModel.stopTimer()
                                }
                                "Business card" -> {
                                    navController.navigate(Screens.BusinessCard.route)
                                }
                                "Woof" -> {
                                    navController.navigate(Screens.Woof.route)
                                }
                                else -> {
                                    toastMessage(
                                        context = context,
                                        message = "Preview not found."
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.Preview,
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = {
                            // save article to bookmarks and modify icon.
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(state = verticalScroll)
                .padding(horizontal = 16.dp)
        ) {
            when (courseDetails) {
                null -> {
                    DetailsNotFound()
                }
                else -> {
                    DetailsItem(course = courseDetails!!)
                }
            }
        }
    }
}

@Composable
private fun DetailsNotFound() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Course details not found, Please check the ID and try again",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = {
                // Handle retry logic here
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.retry)
            )
        }
    }
}

@Composable
private fun DetailsItem(course: CourseDetails) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            AsyncImage(
                model = course.imageUrl ?: R.drawable.compose_logo,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RectangleShape)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                ) {
                    Text(
                        text = course.author ?: "John Doe",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_extra_bold)),
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight,
                            letterSpacing = MaterialTheme.typography.bodyLarge.letterSpacing,
                        )
                    )
                    Text(
                        text = course.publishedDate ?: "Published · Oct, 28 2025",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular)),
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                            lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                            letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        ),
                    )
                }
                TextButton(
                    onClick = {
                        toastMessage(
                            context = context,
                            message = "In progress. This feature will be implemented soon"
                        )
                    }
                ) {
                    Text(
                        text = stringResource(R.string.follow)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = course.description ?: "No description available",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                    letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                ),
            )

            val intent = if (course.web_url.isNullOrEmpty()) {
                Intent(
                    Intent.ACTION_VIEW,
                    "https://www.example.com".toUri()
                )
            } else {
                Intent(
                    Intent.ACTION_VIEW,
                    course.web_url.toUri()
                )
            }

            val openAlertDialog = remember { mutableStateOf(false) }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    openAlertDialog.value = true
                }
            ) {
                Text(
                    text = stringResource(R.string.take_codelab_btn_text)
                )
            }

            if (openAlertDialog.value) {

                CustomAlertDialog(
                    onDismissRequest = {
                        openAlertDialog.value = false
                    },
                    onConfirmation = {
                        openAlertDialog.value = false
                        context.startActivity(intent)
                    },
                    dialogTitle = stringResource(R.string.warning),
                    dialogText = stringResource(R.string.dialog_txt),
                    icon = Icons.Default.Info,
                    confirmText = stringResource(R.string.confirm),
                    dismissText = stringResource(R.string.dismiss)
                )
            }
        }
    }
}