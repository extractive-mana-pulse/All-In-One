package com.example.allinone.main.presentation.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.allinone.R
import com.example.allinone.navigation.HomeScreens
import com.example.allinone.navigation.ProfileScreens
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.util.Locale

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val avatar: String
)

data class Course(
    val id: Int,
    val title: String? = null,
    val subtitle: String? = null,
    val description: String? = null,
    val imageUrl: String? = null
)

data class Sections(
    val id: Int,
    val name: String,
    val imageUrl: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    topBarState: MutableState<Boolean>,
    drawerState: DrawerState
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val courses = remember { loadCoursesFromJson(context) }
    val sections = remember { loadSectionsFromJson(context) }
    var searchHistory = remember { mutableStateListOf<String>() }
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
                            query = query,
                            onQueryChange = { query = it },
                            onSearch = { newQuery ->
                                searchHistory.add(newQuery)
                                active = false
                                query = ""
                            },
                            active = active,
                            onActiveChange = { active = it },
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
                                        Icon(
                                            Icons.Default.AccountCircle,
                                            contentDescription = stringResource(R.string.search_bar_profile_icon)
                                        )
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
                            }
                        ) {
                            if (searchHistory.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.search_history_empty),
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            } else {
                                searchHistory.take(10).forEach { item ->
                                    ListItem(
                                        modifier = Modifier.clickable { query = item },
                                        headlineContent = { Text(text = item) },
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
            items(courses) {
                CourseListItem(
                    navController = navController,
                    course = it
                )
            }
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
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(sections) { section ->
                        ItemCard(sections = section)
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
    if (course.title.isNullOrEmpty() && course.subtitle.isNullOrEmpty()) return

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(
                    HomeScreens.DetailsScreen(
                        id = course.id
                    )
                )
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
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular)),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                    letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                    lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                    platformStyle = MaterialTheme.typography.bodySmall.platformStyle,
                    textAlign = MaterialTheme.typography.bodySmall.textAlign,
                    textDirection = MaterialTheme.typography.bodySmall.textDirection,
                ),
            )
        }
    }
    HorizontalDivider()
}

@Composable
fun ItemCard(sections: Sections) {
    Card(
        modifier = Modifier.size(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier.padding(12.dp).align(Alignment.CenterHorizontally),
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
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = sections.name,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

fun loadCoursesFromJson(context: Context) : List<Course> {
    try {
        // Open and read the JSON file from assets
        val jsonString = context.assets.open("course.json").bufferedReader().use { it.readText() }

        // Parse JSON using Gson
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
        // Open and read the JSON file from assets
        val jsonString = context.assets.open("sections.json").bufferedReader().use { it.readText() }

        // Parse JSON using Gson
        val gson = Gson()
        val courseType = object : TypeToken<List<Sections>>() {}.type
        return gson.fromJson(jsonString, courseType)
    } catch (e: Exception) {
        e.printStackTrace()
        return emptyList()
    }
}