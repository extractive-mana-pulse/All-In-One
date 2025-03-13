package com.example.allinone.settings

import android.content.Context
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.R
import com.example.allinone.core.extension.toastMessage
import com.example.allinone.screens.PartialBottomSheet
import com.example.allinone.screens.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SettingScreen(
    navController: NavHostController = rememberNavController()
) {
    val context: Context = LocalContext.current

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
                title = "Auto-Night Mode",
                description = "This mode defines appearance of the application.",
                icon = Icons.Default.NightsStay,
                onClick = {
                    navController.navigate(Screens.Night.route)
                }
            )
            SettingsItemWithSheet(
                modifier = Modifier,
                title = "Language",
                description = "Change app language to appropriate language for you.",
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
    modifier: Modifier = Modifier,
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
                    text = "Select Language",
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
        modifier = modifier
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

@Composable
fun RadioButtonSingleSelection(modifier: Modifier = Modifier) {
    val radioOptions = listOf(
        "English",
        "Russian",
        "Uzbek"
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Column(modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null // null recommended for accessibility with screen readers
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}