package com.example.presentation.home

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.AppBarWithSearch
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.allinone.core.presentation.R
import com.example.domain.model.UserData
import com.example.presentation.components.Loading
import com.example.presentation.home.components.CodelabListItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun HomeScreenRoot(
    onNavigateToHelp: () -> Unit = {},
    onNavigateToBlogs: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToCodelabs: () -> Unit = {},
    onNavigateToPlCoding: () -> Unit = {},
    onNavigateToDetailWithId: (Int) -> Unit = {},
    drawerState: DrawerState,
    userData: UserData?
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val state by homeViewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        homeViewModel.event.collect { event ->
            when (event) {
                is HomeEvent.Error -> snackbarHostState.showSnackbar(message = event.message)
            }
        }
    }

    HomeScreen(
        snackbarHostState = snackbarHostState,
        onAction = { action ->
            when (action) {
                is HomeScreenAction.OnItemClick -> onNavigateToDetailWithId(action.id.toInt())
                HomeScreenAction.OnNavigationDrawerClick -> {
                    scope.launch { drawerState.apply { if (isClosed) open() else close() } }
                }
                HomeScreenAction.OnNavigationDrawerClick.OnDrawerBlogsClick -> onNavigateToBlogs()
                HomeScreenAction.OnNavigationDrawerClick.OnDrawerCodelabsClick -> onNavigateToCodelabs()
                HomeScreenAction.OnNavigationDrawerClick.OnDrawerPlCodingClick -> onNavigateToPlCoding()
                HomeScreenAction.OnNavigationDrawerClick.OnDrawerHelpClick -> onNavigateToHelp()
                HomeScreenAction.OnNavigationDrawerClick.OnDrawerSettingsClick -> onNavigateToSettings()
                HomeScreenAction.OnProfileClick -> onNavigateToProfile()
                else -> Unit
            }
            homeViewModel.onAction(action)
        },
        state = state,
        userData = userData,
        scope = scope
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onAction: (HomeScreenAction) -> Unit = {},
    state: HomeUiState,
    userData: UserData?,
    scope: CoroutineScope
) {
    val searchHistory = remember { mutableStateListOf<String>() }

    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText = result.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.get(0)
            if (spokenText != null) {
                onAction(HomeScreenAction.OnSearchQueryChange(spokenText))
            }
        }
    }

    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState(initialText = state.searchQuery)
    val isExpanded = searchBarState.currentValue == SearchBarValue.Expanded
    val isCollapsed = searchBarState.currentValue == SearchBarValue.Collapsed

    val scrollBehavior = SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()

    LaunchedEffect(textFieldState.text) {
        onAction(HomeScreenAction.OnSearchQueryChange(textFieldState.text.toString()))
    }

    val profileModel = userData?.profilePictureUrl ?: run {
        val initials = userData?.username
            ?.split(" ")?.mapNotNull { it.firstOrNull() }?.take(2)?.joinToString("") ?: "U"
        "https://ui-avatars.com/api/?name=$initials&size=200&background=4285f4&color=fff&bold=true"
    }

    val inputField = @Composable {
        SearchBarDefaults.InputField(
            searchBarState = searchBarState,
            textFieldState = textFieldState,
            onSearch = { query ->
                if (query.isNotBlank()) searchHistory.add(query)
                scope.launch { searchBarState.animateToCollapsed() }
                onAction(HomeScreenAction.ClearSearch)
            },
            placeholder = {
                if (isCollapsed) {
                    Text(
                        text = "Search in collections",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clearAndSetSemantics {}
                    )
                }
            },
            leadingIcon = {
                if (isExpanded) {
                    IconButton(onClick = {
                        scope.launch { searchBarState.animateToCollapsed() }
                        onAction(HomeScreenAction.ClearSearch)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.outline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                }
            },
            trailingIcon = {
                when {
                    isExpanded && textFieldState.text.isEmpty() -> {
                        IconButton(onClick = {
                            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                                putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to search...")
                            }
                            speechRecognizerLauncher.launch(intent)
                        }) {
                            Icon(painter = painterResource(R.drawable.outline_mic_24), contentDescription = null)
                        }
                    }
                    isExpanded && textFieldState.text.isNotEmpty() -> {
                        IconButton(onClick = {
                            textFieldState.edit { replace(0, length, "") }
                            onAction(HomeScreenAction.ClearSearch)
                        }) {
                            Icon(painter = painterResource(R.drawable.outline_close_24), contentDescription = null)
                        }
                    }
                }
            }
        )
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppBarWithSearch(
                state = searchBarState,
                inputField = inputField,
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { onAction(HomeScreenAction.OnNavigationDrawerClick) }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_menu_24),
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onAction(HomeScreenAction.OnProfileClick) }) {
                        AsyncImage(
                            model = profileModel,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        when {
            state.isLoading -> Loading()
            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
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
                            state.codelabs.forEach { codelab ->
                                CodelabListItem(
                                    codelab = codelab,
                                    onNavigateToDetailWithId = {
                                        onAction(HomeScreenAction.OnItemClick(codelab.id))
                                    },
                                    isLastItem = codelab == state.codelabs.last()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    ExpandedFullScreenSearchBar(
        state = searchBarState,
        inputField = inputField,
    ) {
        if (textFieldState.text.isNotEmpty()) {
            if (state.codelabsFiltered.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No results for \"${state.searchQuery}\"",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                Text(
                    text = "Results",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
                state.codelabsFiltered.forEach { codelab ->
                    ListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onAction(HomeScreenAction.OnItemClick(codelab.id))
                                scope.launch { searchBarState.animateToCollapsed() }
                                onAction(HomeScreenAction.ClearSearch)
                            },
                        headlineContent = {
                            Text(
                                text = codelab.title,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold))
                                )
                            )
                        },
                        supportingContent = {
                            Text(
                                text = codelab.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1
                            )
                        },
                        leadingContent = {
                            AsyncImage(
                                model = codelab.imageUrl.ifEmpty { R.drawable.compose_logo },
                                contentDescription = null,
                                modifier = Modifier.size(40.dp).clip(CircleShape),
                                contentScale = ContentScale.Fit
                            )
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                        )
                    )
                }
            }
        } else {
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
                            .fillMaxWidth()
                            .clickable {
                                textFieldState.edit { replace(0, length, item) }
                                onAction(HomeScreenAction.OnSearchQueryChange(item))
                            },
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
}