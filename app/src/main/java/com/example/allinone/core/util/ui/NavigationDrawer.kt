package com.example.allinone.core.util.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.BuildConfig
import com.example.allinone.R
import com.example.allinone.navigation.screen.HomeScreens
import com.example.allinone.navigation.screen.SettingsScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    content: @Composable (PaddingValues) -> Unit,
    navController: NavHostController = rememberNavController(),
    bottomBarState: MutableState<Boolean>,
    gesturesEnabledState: MutableState<Boolean>,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerContent(
                    navController = navController,
                    drawerState = drawerState
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
            }
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}

@Composable
private fun NavigationDrawerContent(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState
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
                modifier = Modifier.padding(vertical = 16.dp),
                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider()

            Text(
                "Main content",
                modifier = Modifier.padding(vertical = 16.dp),
                style = MaterialTheme.typography.titleMedium
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

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = "Settings & Help",
                modifier = Modifier.padding(vertical = 16.dp),
                style = MaterialTheme.typography.titleMedium
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
                    scope.launch { drawerState.close() }
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
                    scope.launch { drawerState.close() }
                    navController.navigate(HomeScreens.Help.route)
                },
            )

            // Add spacer to push version to bottom
            Spacer(modifier = Modifier.weight(1f))
        }

        // Version text at bottom
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