package com.example.allinone.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.BuildConfig
import com.example.allinone.R
import com.example.allinone.navigation.screen.HomeScreens
import com.example.allinone.navigation.screen.SettingsScreens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    content: @Composable (PaddingValues) -> Unit,
    navController: NavHostController = rememberNavController(),
    bottomBarState: MutableState<Boolean>,
    gesturesEnabledState: MutableState<Boolean>,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    onNavigateToPLCoding: () -> Unit
) {
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerContent(
                    navController = navController,
                    drawerState = drawerState,
                    onNavigateToPLCoding = onNavigateToPLCoding
                )
            }
        },
        drawerState = drawerState,
        gesturesEnabled = gesturesEnabledState.value
    ) {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    bottomBarState = bottomBarState,
                    navController = navController
                )
            },
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}

@Composable
private fun NavigationDrawerContent(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState,
    onNavigateToPLCoding: () -> Unit
) {
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.app_name),
                modifier = Modifier
                    .padding(vertical = 16.dp),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                    letterSpacing = MaterialTheme.typography.titleLarge.letterSpacing,
                    lineHeight = MaterialTheme.typography.titleLarge.lineHeight,
                    platformStyle = MaterialTheme.typography.titleLarge.platformStyle,
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold))
                )
            )

            HorizontalDivider()

            Text(
                text = stringResource(R.string.main_content),
                modifier = Modifier.padding(vertical = 16.dp),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    letterSpacing = MaterialTheme.typography.titleMedium.letterSpacing,
                    lineHeight = MaterialTheme.typography.titleMedium.lineHeight,
                    platformStyle = MaterialTheme.typography.titleMedium.platformStyle,
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold))
                )
            )

            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Blogs"
                    )
                },
                selected = false,
                icon = {
                    Icon(
                        Icons.Outlined.Folder,
                        contentDescription = stringResource(R.string.folder_icon)
                    )
                },
                onClick = { /* Handle click */ }
            )

            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Codelabs"
                    )
                },
                selected = false,
                icon = {
                    Icon(
                        Icons.Outlined.Folder,
                        contentDescription = stringResource(R.string.folder_icon)
                    )
                },
                onClick = {  }
            )

            NavigationDrawerItem(
                label = {
                    Text(
                        text = "PL Coding"
                    )
                },
                selected = false,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.pl_coding_logo),
                        contentDescription = stringResource(R.string.folder_icon),
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp).clip(CircleShape)
                    )
                },
                onClick = {
                    onNavigateToPLCoding()
                }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = stringResource(R.string.settings_and_help),
                modifier = Modifier.padding(vertical = 16.dp),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    letterSpacing = MaterialTheme.typography.titleMedium.letterSpacing,
                    lineHeight = MaterialTheme.typography.titleMedium.lineHeight,
                    platformStyle = MaterialTheme.typography.titleMedium.platformStyle,
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold))
                )
            )
            NavigationDrawerItem(
                label = {
                    Text(
                        text = stringResource(R.string.settings)
                    )
                },
                selected = false,
                icon = {
                    Icon(
                        Icons.Outlined.Settings,
                        contentDescription = stringResource(R.string.settingsIcon)
                    )
                },
                onClick = {
                    scope.launch(Dispatchers.Main) { drawerState.close() }
                    navController.navigate(SettingsScreens.Settings.route)
                }
            )
            NavigationDrawerItem(
                label = {
                    Text(
                        text = stringResource(R.string.help_and_feedback)
                    )
                },
                selected = false,
                icon = {
                    Icon(
                        Icons.AutoMirrored.Outlined.HelpOutline,
                        contentDescription = stringResource(R.string.help_and_feedback)
                    )
                },
                onClick = {
                    scope.launch(Dispatchers.Main) { drawerState.close() }
                    navController.navigate(HomeScreens.Help.route)
                },
            )

            Spacer(modifier = Modifier.weight(1f))
        }
        Text(
            text = stringResource(R.string.app_version) + " " +BuildConfig.VERSION_NAME,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}