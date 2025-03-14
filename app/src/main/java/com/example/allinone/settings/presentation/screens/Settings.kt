package com.example.allinone.settings.presentation.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.R
import com.example.allinone.core.helper.LanguageChangeHelper
import com.example.allinone.navigation.Screens

val languageChangeHelper by lazy { LanguageChangeHelper() }

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SettingScreen(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            SettingsItem(
                modifier = Modifier,
                title = stringResource(R.string.auto_nigt_mode),
                description = stringResource(R.string.auto_nigt_mode_desc),
                icon = Icons.Default.NightsStay,
                onClick = {
                    navController.navigate(Screens.Night.route)
                }
            )
            SettingsItemWithSheet(
                title = stringResource(R.string.language),
                description = stringResource(R.string.language_desc),
                icon = Icons.Default.Language,
            )
            SettingsItem(
                modifier = Modifier,
                title = "Tip Calculator",
                description = "In this code lab you will learn how to build a tip calculator app.",

                onClick = {
                    navController.navigate(Screens.TipCalculator.route)
                }
            )
            SettingsItem(
                title = "Art Space App",
                description = "In this section you will learn how to build an art space app.",
                onClick = {
                    navController.navigate(Screens.ArtSpace.route)
                }
            )
            SettingsItem(
                title = "Lemonade",
                description = "In this section you will learn how to build an art space app.",
                onClick = {
                    navController.navigate(Screens.Lemonade.route)
                }
            )
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
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                ) },
            supportingContent = {
                Text(
                    text = description,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
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
            trailingContent = { Text("") }
        )
        HorizontalDivider(
            modifier = Modifier.padding(start = 80.dp),
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
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                ) },
            supportingContent = {
                Text(
                    text = description,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
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
            trailingContent = { Text("") }
        )
        HorizontalDivider(
            modifier = Modifier.padding(start = 80.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
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