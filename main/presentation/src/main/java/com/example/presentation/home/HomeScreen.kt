package com.example.presentation.home

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.allinone.core.presentation.R
import com.example.domain.model.Codelab
import com.example.domain.model.Sections
import com.example.domain.model.UserData
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit = {},
    onNavigateToDetailWithId: (Int) -> Unit = {},
    drawerState: DrawerState,
    userData: UserData?
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val state by homeViewModel.state.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    var active by remember { mutableStateOf(false) }
    val searchHistory = remember { mutableStateListOf<String>() }

    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText = result.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.get(0)
            if (spokenText != null) {
                homeViewModel.onAction(HomeAction.OnSearchQueryChange(spokenText))
            }
        }
    }

    Scaffold(
        topBar = {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(if (active) 0.dp else 16.dp),
                inputField = {
                    SearchBarDefaults.InputField(
                        query = state.searchQuery,
                        onQueryChange = {
                            homeViewModel.onAction(HomeAction.OnSearchQueryChange(it))
                        },
                        onSearch = { newQuery ->
                            if (newQuery.isNotBlank()) searchHistory.add(newQuery)
                            active = false
                            homeViewModel.onAction(HomeAction.ClearSearch)
                        },
                        expanded = active,
                        onExpandedChange = { active = it },
                        placeholder = {
                            Text(text = stringResource(R.string.search))
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
                                        painter = painterResource(R.drawable.baseline_menu_24),
                                        contentDescription = null
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = {
                                        active = false
                                        homeViewModel.onAction(HomeAction.ClearSearch)
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.outline_arrow_back_24),
                                        contentDescription = null
                                    )
                                }
                            }
                        },
                        trailingIcon = {
                            if (!active) {
                                IconButton(onClick = onNavigateToProfile) {
                                    AsyncImage(
                                        model = userData?.profilePictureUrl ?: run {
                                            val initials = userData?.username
                                                ?.split(" ")
                                                ?.mapNotNull { it.firstOrNull() }
                                                ?.take(2)
                                                ?.joinToString("")
                                                ?: "U"
                                            "https://ui-avatars.com/api/?name=$initials&size=200&background=4285f4&color=fff&bold=true"
                                        },
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                    )
                                }
                            } else {
                                if (state.searchQuery.isEmpty()) {
                                    IconButton(onClick = {
                                        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                                            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                                            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to search...")
                                        }
                                        speechRecognizerLauncher.launch(intent)
                                    }) {
                                        Icon(
                                            painter = painterResource(R.drawable.outline_mic_24),
                                            contentDescription = null
                                        )
                                    }
                                } else {
                                    IconButton(
                                        onClick = {
                                            homeViewModel.onAction(HomeAction.ClearSearch)
                                        }
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.outline_close_24),
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        }
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
                                    homeViewModel.onAction(HomeAction.OnSearchQueryChange(item))
                                    active = false
                                }
                                .fillMaxWidth(),
                            headlineContent = {
                                Text(
                                    text = item,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold)),
                                    )
                                )
                            },
                            leadingContent = {
                                Icon(
                                    painter = painterResource(R.drawable.outline_history_24),
                                    contentDescription = null
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
    ) { innerPadding ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.error ?: "Unknown error",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            state.codelabsFiltered.isEmpty() && state.searchQuery.isNotBlank() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No results for \"${state.searchQuery}\"",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                ) {
                    item {
                        Text(
                            text = stringResource(R.string.courses),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontFamily = FontFamily(Font(R.font.inknut_antiqua_extra_bold)),
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(vertical = 8.dp)
                        )
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(24.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(vertical = 8.dp)
                        ) {
                            state.codelabsFiltered.forEach { codelab ->
                                CodelabListItem(
                                    codelab = codelab,
                                    onNavigateToDetailWithId = onNavigateToDetailWithId,
                                    isLastItem = codelab == state.codelabsFiltered.last()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CodelabListItem(
    modifier: Modifier = Modifier,
    codelab: Codelab,
    onNavigateToDetailWithId: (Int) -> Unit,
    isLastItem: Boolean = false
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToDetailWithId(codelab.id.toIntOrNull() ?: 0) }
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = codelab.imageUrl.ifEmpty { R.drawable.compose_logo },
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = codelab.title,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold)),
                    )
                )
                Text(
                    text = codelab.subtitle,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        if (!isLastItem) {
            HorizontalDivider(color = MaterialTheme.colorScheme.surface)
        }
    }
}
@Composable
fun ItemCard(
    onNavigateToSectionById: (Int) -> Unit = {},
    sections: Sections
) {
    Card(
        modifier = Modifier
            .size(120.dp)
            .clickable {
                sections.id?.let { id -> onNavigateToSectionById(id) }
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
                model = sections.imageUrl ?: R.drawable.compose_logo,
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