package com.example.allinone.main.presentation.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.allinone.R
import com.example.allinone.auth.domain.model.UserCredentials
import com.example.allinone.core.extension.toastMessage
import com.example.allinone.main.domain.model.Course
import com.example.allinone.main.domain.model.Sections
import com.example.allinone.main.presentation.vm.TimerViewModel
import com.example.allinone.navigation.screen.HomeScreens
import com.example.allinone.navigation.screen.ProfileScreens
import com.example.allinone.settings.readingMode.presentation.vm.ReadingModeViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    topBarState: MutableState<Boolean>,
    drawerState: DrawerState,
    userCredentials: UserCredentials?
) {

    val context = LocalContext.current
    val timerViewModel: TimerViewModel = hiltViewModel()
    val readingViewModel: ReadingModeViewModel = hiltViewModel()
    val timerValue by timerViewModel.timer.collectAsStateWithLifecycle()
    val readingMode by readingViewModel.isReadingModeEnabled.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var rowVisible by remember { mutableStateOf(false) }
    val courses = remember { loadCoursesFromJson(context) }
    val snackbarHostState = remember { SnackbarHostState() }
    val sections = remember { loadSectionsFromJson(context) }
    var searchHistory = remember { mutableStateListOf<String>() }

    LaunchedEffect(readingMode) {
        if (readingMode)
            timerViewModel.startTimer()
        else
            rowVisible = false
    }

    LaunchedEffect(timerValue) {
        if (readingMode && timerViewModel.readingModeSnackbar(5)) {

            val result = snackbarHostState.showSnackbar(
                message = "Would you like to turn off reading mode?",
                actionLabel = "Turn Off",
                duration = SnackbarDuration.Long,
            )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    readingViewModel.disableReadingMode(false)
                    rowVisible = false
                }
                SnackbarResult.Dismissed -> {
                    rowVisible = true
                }
            }
        }
    }

    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val spokenText: String? = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
            if (spokenText != null) query = spokenText
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            AnimatedVisibility(
                visible = topBarState.value,
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SearchBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(if (active) 0.dp else 16.dp),
                            inputField = {
                                SearchBarDefaults.InputField(
                                    query = query,
                                    onQueryChange = { query = it },
                                    onSearch = { newQuery ->
                                        searchHistory.add(newQuery)
                                        active = false
                                        query = ""
                                    },
                                    expanded = active,
                                    onExpandedChange = { active = it },
                                    placeholder = {
                                        Text(
                                            text = stringResource(R.string.search)
                                        )
                                    },
                                    leadingIcon = {
                                        if (!active) {
                                            IconButton(
                                                onClick = {
                                                    scope.launch {
                                                        drawerState.apply {
                                                            if (isClosed) open() else close()
                                                        }
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    Icons.Default.Menu,
                                                    contentDescription = stringResource(R.string.search_bar_menu_icon)
                                                )
                                            }
                                        } else {
                                            IconButton(
                                                onClick = {
                                                    active = false
                                                    query = ""
                                                }
                                            ) {
                                                Icon(
                                                    Icons.AutoMirrored.Default.ArrowBack,
                                                    contentDescription = stringResource(R.string.search_bar_close_icon)
                                                )
                                            }
                                        }
                                    },
                                    trailingIcon = {
                                        if (!active) {
                                            IconButton(
                                                onClick = {
                                                    navController.navigate(ProfileScreens.Profile.route)
                                                }
                                            ) {
                                                if (userCredentials?.imageUrl != null) {
                                                    AsyncImage(
                                                        model = userCredentials.imageUrl,
                                                        contentScale = ContentScale.Crop,
                                                        contentDescription = stringResource(R.string.search_bar_profile_icon)
                                                    )
                                                } else {
                                                    Icon(
                                                        Icons.Default.AccountCircle,
                                                        contentDescription = stringResource(R.string.search_bar_profile_icon)
                                                    )
                                                }
                                            }
                                        } else {
                                            Row {
                                                if (query.isEmpty()) {
                                                    IconButton(onClick = {
                                                        val intent =
                                                            Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                                                putExtra(
                                                                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                                                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                                                                )
                                                                putExtra(
                                                                    RecognizerIntent.EXTRA_LANGUAGE,
                                                                    Locale.getDefault()
                                                                )
                                                                putExtra(
                                                                    RecognizerIntent.EXTRA_PROMPT,
                                                                    "Speak to search..."
                                                                )
                                                            }
                                                        speechRecognizerLauncher.launch(intent)
                                                    }) {
                                                        Icon(
                                                            Icons.Default.Mic,
                                                            contentDescription = stringResource(R.string.search_bar_mic_icon)
                                                        )
                                                    }
                                                } else {
                                                    IconButton(
                                                        onClick = {
                                                            query = ""
                                                        }
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Default.Close,
                                                            contentDescription = stringResource(R.string.search_bar_clear_icon)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    },
                                )
                            },
                            expanded = active,
                            onExpandedChange = { active = it },
                        ) {
                            if (searchHistory.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.search_history_empty),
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            } else {
                                searchHistory.forEach { item ->
                                    ListItem(
                                        modifier = Modifier
                                            .clickable {
                                                query = item
                                                active = false
                                            }
                                            .fillMaxWidth(),
                                        headlineContent = {
                                            Text(
                                                text = item,
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    color = MaterialTheme.colorScheme.primary,
                                                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold)),
                                                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                                                    letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                                                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                                                    platformStyle = MaterialTheme.typography.bodyMedium.platformStyle,
                                                    textAlign = MaterialTheme.typography.bodyMedium.textAlign,
                                                    textDirection = MaterialTheme.typography.bodyMedium.textDirection,
                                                )
                                            )
                                        },
                                        leadingContent = {
                                            Icon(
                                                Icons.Default.History,
                                                contentDescription = stringResource(R.string.search_bar_history_icon)
                                            )
                                        },
                                        colors = ListItemDefaults.colors(
                                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            if (rowVisible) {
                item {
                    AnimatedVisibility(
                        visible = rowVisible,
                        enter = fadeIn(animationSpec = tween(durationMillis = 3000)) +
                                slideInHorizontally(animationSpec = tween(durationMillis = 3000)) { fullWidth ->
                                    -fullWidth / 3
                                },
                        exit = fadeOut(animationSpec = tween(durationMillis = 3000)) +
                                slideOutHorizontally(animationSpec = tween(durationMillis = 3000)) { fullWidth ->
                                    fullWidth / 3
                                }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.home_screen_custom_row_txt),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextButton(
                                    onClick = {
                                        readingViewModel.disableReadingMode(false)
                                        rowVisible = false
                                    }
                                ) {
                                    Text(
                                        text = stringResource(R.string.turn_off),
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        rowVisible = false
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
            /** Courses section text. */
            item {
                Text(
                    text = stringResource(R.string.courses),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_extra_bold)),
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                        letterSpacing = MaterialTheme.typography.bodyLarge.letterSpacing,
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight,
                        platformStyle = MaterialTheme.typography.bodyLarge.platformStyle,
                        textAlign = MaterialTheme.typography.bodyLarge.textAlign,
                        textDirection = MaterialTheme.typography.bodyLarge.textDirection,
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            /** Courses list. */
            items(
                courses,
                key = { it.id }
            ) {
                CourseListItem(
                    navController = navController,
                    course = it
                )
            }
            /** Other Sections text. */
            item {
                Text(
                    text = stringResource(R.string.other_sections),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_extra_bold)),
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                        letterSpacing = MaterialTheme.typography.bodyLarge.letterSpacing,
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight,
                        platformStyle = MaterialTheme.typography.bodyLarge.platformStyle,
                        textAlign = MaterialTheme.typography.bodyLarge.textAlign,
                        textDirection = MaterialTheme.typography.bodyLarge.textDirection,
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            /** Other Sections list. */
            item {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    maxItemsInEachRow = 3,
                ) {
                    sections.forEach { section ->
                        ItemCard(
                            navController = navController,
                            sections = section
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CourseListItem(
    navController: NavHostController = rememberNavController(),
    course: Course,
) {
    val context = LocalContext.current
    if (course.title.isNullOrEmpty() && course.subtitle.isNullOrEmpty()) return

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                when (course.title == "In maintenance" && course.subtitle == "In maintenance") {
                    true -> toastMessage(
                        context = context,
                        message = "This page is not available now."
                    )
                    else -> navController.navigate(
                        HomeScreens.DetailsScreen(
                            id = course.id
                        )
                    )
                }
            }
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = course.imageUrl ?: R.drawable.compose_logo,
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
//      title and subtitle column
        Column {
            Text(
                text = course.title ?: "",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold)),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                    letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                    platformStyle = MaterialTheme.typography.bodyMedium.platformStyle,
                    textAlign = MaterialTheme.typography.bodyMedium.textAlign,
                    textDirection = MaterialTheme.typography.bodyMedium.textDirection,
                ),
            )
            Text(
                text = course.subtitle ?: "",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                    letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                    lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                    platformStyle = MaterialTheme.typography.bodySmall.platformStyle,
                    textAlign = MaterialTheme.typography.bodySmall.textAlign,
                    textDirection = MaterialTheme.typography.bodySmall.textDirection,
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    HorizontalDivider()
}

@Composable
fun ItemCard(
    navController : NavHostController = rememberNavController(),
    sections: Sections
) {
    Card(
        modifier = Modifier
            .size(120.dp)
            .clickable {
                navController.navigate(
                    HomeScreens.SectionScreen(
                        id = sections.id ?: 0
                    )
                )
            },
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = sections.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = sections.name ?: "Section not found",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                    letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                    lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                    platformStyle = MaterialTheme.typography.bodySmall.platformStyle,
                    textAlign = MaterialTheme.typography.bodySmall.textAlign,
                    textDirection = MaterialTheme.typography.bodySmall.textDirection,
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

fun loadCoursesFromJson(context: Context) : List<Course> {
    try {
        val jsonString = context.assets.open("course.json").bufferedReader().use { it.readText() }

        val gson = Gson()
        val courseType = object : TypeToken<List<Course>>() {}.type
        return gson.fromJson(jsonString, courseType)
    } catch (e: Exception) {
        e.printStackTrace()
        return emptyList()
    }
}

fun loadSectionsFromJson(context: Context) : List<Sections> {
    try {
        val jsonString = context.assets.open("sections.json").bufferedReader().use { it.readText() }

        val gson = Gson()
        val courseType = object : TypeToken<List<Sections>>() {}.type
        return gson.fromJson(jsonString, courseType)
    } catch (e: Exception) {
        e.printStackTrace()
        return emptyList()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleSearchBar(
    textFieldState: TextFieldState,
    onSearch: (String) -> Unit,
    searchResults: List<String>,
    modifier: Modifier = Modifier
) {
    // Controls expansion state of the search bar
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = { textFieldState.edit { replace(0, length, it) } },
                    onSearch = {
                        onSearch(textFieldState.text.toString())
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Search") }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                searchResults.forEach { result ->
                    ListItem(
                        headlineContent = { Text(result) },
                        modifier = Modifier
                            .clickable {
                                textFieldState.edit { replace(0, length, result) }
                                expanded = false
                            }
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}