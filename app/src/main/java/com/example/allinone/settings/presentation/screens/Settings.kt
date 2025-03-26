package com.example.allinone.settings.presentation.screens

import android.content.Intent
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
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.R
import com.example.allinone.core.helper.LanguageChangeHelper
import com.example.allinone.navigation.SettingsScreens
import com.example.allinone.settings.presentation.vm.ReadingModeViewModel

val languageChangeHelper by lazy { LanguageChangeHelper() }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavHostController = rememberNavController()
) {
    val viewModel: ReadingModeViewModel = viewModel()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold)),
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
            /** Auto-Night Mode */
            item {
                SettingsItem(
                    title = stringResource(R.string.auto_nigt_mode),
                    description = stringResource(R.string.auto_nigt_mode_desc),
                    icon = Icons.Default.NightsStay,
                    onClick = { navController.navigate(SettingsScreens.Night.route) }
                )
            }

            /** Language */
            item {
                SettingsItemWithSheet(
                    title = stringResource(R.string.language),
                    description = stringResource(R.string.language_desc),
                    icon = Icons.Default.Language
                )
            }

            /** Reading Mode */
            item {
                SettingsItemWithToggle(
                    modifier = Modifier,
                    title = stringResource(R.string.reading_mode),
                    description = stringResource(R.string.reading_mode_desc),
                    icon = Icons.AutoMirrored.Default.MenuBook,
                    viewModel = viewModel
                )
            }

            /** Power Saving Mode */
            item {
                SettingsItem(
                    title = stringResource(R.string.power_saving),
                    description = stringResource(R.string.power_saving_desc),
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
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Box(
                    modifier = Modifier.size(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold)),
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                        letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                        lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                        letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
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
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.select_language),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold))
                    ),
                )
                RadioButtonSingleSelection()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{ showBottomSheet = true }
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold)),
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                        letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            supportingContent = {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                        lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                        letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            },
            leadingContent = {
                icon?.let {
                    Box(
                        modifier = Modifier.size(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }
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
//    val contentAlpha = if (readingMode) ContentAlpha.medium else ContentAlpha.high

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
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold)),
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                        letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                        lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                        letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
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

private val languageMapping = mapOf(
    "English" to "en",
    "Russian" to "ru",
    "Uzbek" to "uz"
)

@Composable
fun RadioButtonSingleSelection() {
    val context = LocalContext.current
    val currentLanguageCode = languageChangeHelper.getLanguageCode(context)

    val initialLanguage = languageMapping.entries.find { it.value == currentLanguageCode }?.key ?: "English"

    var selectedOption by remember { mutableStateOf(initialLanguage) }

    Column(Modifier.selectableGroup()) {
        languageMapping.keys.forEach { language ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (language == selectedOption),
                        onClick = {
                            selectedOption = language
                            val languageCode = languageMapping[language] ?: "en"
                            languageChangeHelper.changeLanguage(context, languageCode)
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
                    onClick = null
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