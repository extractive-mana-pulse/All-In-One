package com.example.allinone.util.ui

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.allinone.core.extension.toastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale

/* Code is fine but i cannot use method directly cause given attributes does not behave properly. Fix in the future. */

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun searchBarUI(
    active: Boolean,
    query: String,
    searchHistory: SnapshotStateList<String>,
    scope: CoroutineScope,
    drawerState: DrawerState,
    context: Context,
    speechRecognizerLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>
): String {
    var isActive by remember { mutableStateOf(false) }
    isActive = active
    var searchQuery = query
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (isActive) 0.dp else 16.dp),
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = { newQuery ->
                searchHistory.add(newQuery)
                isActive = false
                searchQuery = ""
            },
            active = isActive,
            onActiveChange = { isActive = it },
            placeholder = {
                Text(text = "Search")
            },
            leadingIcon = {
                if (!isActive) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                            toastMessage(context, "Opening navigation drawer")
                        }
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu icon in search bar"
                        )
                    }
                } else {
                    IconButton(
                        onClick = {
                            isActive = false
                            searchQuery = ""
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Close icon in search bar"
                        )
                    }
                }
            },
            trailingIcon = {
                if (!isActive) {
                    IconButton(
                        onClick = {
                            toastMessage(context, "Open user profile")
                        }
                    ) {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = "Person icon in search bar"
                        )
                    }
                } else {
                    Row {
                        if (searchQuery.isEmpty()) {
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
                                    contentDescription = "Microphone icon"
                                )
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    searchQuery = ""
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear search"
                                )
                            }
                        }
                    }
                }
            }
        ) {
            if (searchHistory.isEmpty()) {
                Text(
                    text = "No search history",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                searchHistory.take(10).forEach { item ->
                    ListItem(
                        modifier = Modifier.clickable { searchQuery = item },
                        headlineContent = { Text(text = item) },
                        leadingContent = {
                            Icon(
                                Icons.Default.History,
                                contentDescription = "History icon"
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
    return searchQuery
}