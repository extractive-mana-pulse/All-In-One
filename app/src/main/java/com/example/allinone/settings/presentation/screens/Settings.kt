package com.example.allinone.settings.presentation.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.BatterySaver
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.R
import com.example.allinone.core.helper.LanguageChangeHelper
import com.example.allinone.navigation.Screens
import com.example.allinone.navigation.SettingsScreens
import com.example.allinone.settings.presentation.vm.ReadingModeViewModel

val languageChangeHelper by lazy { LanguageChangeHelper() }

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SettingScreen(
    navController: NavHostController = rememberNavController()
) {
    val viewModel: ReadingModeViewModel = viewModel()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold))
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                SettingsItem(
                    title = stringResource(R.string.auto_nigt_mode),
                    description = stringResource(R.string.auto_nigt_mode_desc),
                    icon = Icons.Default.NightsStay,
                    onClick = { navController.navigate(SettingsScreens.Night.route) }
                )
            }
            item {
                SettingsItemWithSheet(
                    title = stringResource(R.string.language),
                    description = stringResource(R.string.language_desc),
                    icon = Icons.Default.Language
                )
            }
            item {
                SettingsItemWithToggle(
                    modifier = Modifier,
                    title = "Reading mode",
                    description = "Reading mode mostly used for reading articles. it's decrease a light-blue color and make it less pain for your eyes.",
                    icon = Icons.AutoMirrored.Default.MenuBook,
                    viewModel = viewModel
                )
            }
            item {
                SettingsItem(
                    title = "Power saving mode",
                    description = "Automatically reduce power usage and animations when your battery is below 20%. ",
                    icon = Icons.Default.BatterySaver,
                    onClick = {
                        navController.navigate(SettingsScreens.PowerSaving.route)
                    }
                )
            }
        }
    }
}

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: ImageVector? = null,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth().clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Box(
                    modifier = Modifier.size(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold))
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium))
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 80.dp, end = 16.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsItemWithSheet(
    title: String,
    description: String,
    icon: ImageVector? = null,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet = false }
        ) {
            Column {
                Text(
                    text = stringResource(R.string.select_language),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold))
                    ),
                    modifier = Modifier.padding(16.dp)
                )
                RadioButtonSingleSelection()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{ showBottomSheet = true },
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold))

                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                ) },
            supportingContent = {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium))

                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            },
            leadingContent = {
                icon?.let {
                    Image(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                }
            },
        )
        HorizontalDivider(
            modifier = Modifier.padding(start = 80.dp, end = 16.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
fun SettingsItemWithToggle(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: ImageVector? = null,
    viewModel: ReadingModeViewModel = hiltViewModel()
) {
    val sepiaColor = Color(0xFF704214)

    // Get the reading mode state from the ViewModel
    val uiState by viewModel.uiState
    val readingMode = uiState.isReadingModeEnabled

    // Create a ContentAlpha value based on reading mode
    val contentAlpha = if (readingMode) ContentAlpha.medium else ContentAlpha.high

    val backgroundColor = if (readingMode) {
        Color(0xFFF8F1E3)
    } else {
        MaterialTheme.colorScheme.background
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Box(
                    modifier = Modifier.size(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        colorFilter = if (readingMode) ColorFilter.tint(sepiaColor.copy(alpha = 0.7f)) else null
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold)),
                        color = if (readingMode) sepiaColor else LocalContentColor.current
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                        color = if (readingMode) sepiaColor.copy(alpha = 0.8f) else LocalContentColor.current.copy(alpha = contentAlpha)
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box(
                modifier = Modifier.padding(start = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Switch(
                    checked = readingMode,
                    onCheckedChange = { newValue ->
                        // Use the ViewModel to toggle reading mode
                        viewModel.toggleReadingMode(newValue)
                    }
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 80.dp, end = 16.dp),
            thickness = 1.dp,
            color = if (readingMode) sepiaColor.copy(alpha = 0.3f) else MaterialTheme.colorScheme.outline
        )
    }
}

// Map UI labels to language codes
private val languageMapping = mapOf(
    "English" to "en",
    "Russian" to "ru",
    "Uzbek" to "uz"
)

@Composable
fun RadioButtonSingleSelection(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val currentLanguageCode = languageChangeHelper.getLanguageCode(context)

    // Find the UI label for the current language code
    val initialLanguage = languageMapping.entries.find { it.value == currentLanguageCode }?.key ?: "English"

    var selectedOption by remember { mutableStateOf(initialLanguage) }

    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Column(modifier.selectableGroup()) {
        languageMapping.keys.forEach { language ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (language == selectedOption),
                        onClick = {
                            selectedOption = language
                            // Get the language code from our mapping and apply it
                            val languageCode = languageMapping[language] ?: "en"
                            languageChangeHelper.changeLanguage(context, languageCode)
                            // Inside your click handler after changing the language
                            val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
                            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            context.startActivity(intent)
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (language == selectedOption),
                    onClick = null // null recommended for accessibility with screen readers
                )
                Text(
                    text = language,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun ReadingModeTheme(
    viewModel: ReadingModeViewModel,
    content: @Composable () -> Unit
) {
    // Collect the reading mode state from the ViewModel
    val isReadingModeEnabled by viewModel.isReadingModeEnabled.collectAsState()

    // Define colors for reading mode
    val sepiaColor = Color(0xFF704214)
    val sepiaBackground = Color(0xFFF8F1E3)

    // Create theme colors based on the reading mode
    val colors = if (isReadingModeEnabled) {
        // Custom sepia-themed colors for reading mode
        MaterialTheme.colorScheme.copy(
            background = sepiaBackground,
            surface = sepiaBackground,
            onBackground = sepiaColor,
            onSurface = sepiaColor,
            primary = sepiaColor,
            secondary = sepiaColor.copy(alpha = 0.7f),
            tertiary = sepiaColor.copy(alpha = 0.5f),
            outline = sepiaColor.copy(alpha = 0.3f)
        )
    } else {
        // Use the default theme colors
        MaterialTheme.colorScheme
    }

    // Custom typography for reading mode
    val typography = if (isReadingModeEnabled) {
        MaterialTheme.typography.copy(
            bodyLarge = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold))
            ),
            bodyMedium = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium))
            ),
            bodySmall = MaterialTheme.typography.bodySmall.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium))
            )
        )
    } else {
        MaterialTheme.typography
    }

    // Apply the theme
    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        content = content
    )
}