package com.example.allinone.blogs.presentation.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.example.allinone.R
import com.example.allinone.navigation.Screens
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

//data class Course(
//    val id: Int,
//    val title: String,
//    val description: String,
//    val imageUrl: String? = null
//)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    topBarState: MutableState<Boolean>,
    drawerState: DrawerState
) {
    val context = LocalContext.current
    val users = remember { loadUsersFromJson(context) }
    var query by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var active by remember { mutableStateOf(false) }
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
                        modifier = Modifier.fillMaxWidth(),
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
                                            navController.navigate(Screens.Profile.route)
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
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(users.size) { user ->
                UserCard(user = users[user])
            }
        }
    }
}

@Composable
fun UserCard(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar image
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = user.avatar)
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = stringResource(R.string.user_avatar),
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // User details
            Column {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

// Step 4: Create a function to load and parse the JSON file
fun loadUsersFromJson(context: Context): List<User> {
    try {
        // Open and read the JSON file from assets
        val jsonString = context.assets.open("users.json").bufferedReader().use { it.readText() }

        // Parse JSON using Gson
        val gson = Gson()
        val usersType = object : TypeToken<List<User>>() {}.type
        return gson.fromJson(jsonString, usersType)
    } catch (e: Exception) {
        e.printStackTrace()
        return emptyList()
    }
}