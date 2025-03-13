package com.example.allinone.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Help
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.allinone.screens.Screens
import com.example.allinone.util.ui.BottomNavigationBar
import com.example.allinone.util.ui.VisibilityOfUI
import kotlinx.coroutines.launch

@SuppressLint("RestrictedApi")
@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val gesturesEnabledState = rememberSaveable { mutableStateOf(true) }
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val topBarState = rememberSaveable { (mutableStateOf(true)) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    VisibilityOfUI(
        gesturesEnabledState = gesturesEnabledState,
        navBackStackEntry = navBackStackEntry,
        bottomBarState = bottomBarState,
        topBarState = topBarState
    )

    DetailedDrawerExample(
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Graph.HOME,
                modifier = Modifier.padding(
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
//                    top = if (topBarState.value) innerPadding.calculateTopPadding() else 0.dp,
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
//                    bottom = if (bottomBarState.value) innerPadding.calculateBottomPadding() else 0.dp
                )
            ) {
                appNavigationTest(
                    navController = navController,
                    topBarState = topBarState,
                    drawerState = drawerState
                )
            }
        },
        navController = navController,
        bottomBarState = bottomBarState,
        gesturesEnabledState = gesturesEnabledState,
        drawerState = drawerState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedDrawerExample(
    content: @Composable (PaddingValues) -> Unit,
    navController: NavHostController = rememberNavController(),
    bottomBarState: MutableState<Boolean>,
    gesturesEnabledState: MutableState<Boolean>,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
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
private fun DrawerContent(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(12.dp))

        Text(
            "All In One",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleLarge
        )

        HorizontalDivider()

        NavigationDrawerItem(
            label = { Text("Settings") },
            selected = false,
            icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
            badge = { Text("20") }, // Placeholder
            onClick = { /* Handle click */ }
        )

        Text(
            "Section 1",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )

        NavigationDrawerItem(
            label = { Text("Item 1") },
            selected = false,
            onClick = { /* Handle click */ }
        )

        NavigationDrawerItem(
            label = { Text("Item 2") },
            selected = false,
            onClick = {  }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            "Section 2",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
        NavigationDrawerItem(
            label = { Text("Settings") },
            selected = false,
            icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
            onClick = {
                scope.launch {
                    drawerState.close()
                }
                navController.navigate(Screens.Settings.route)
            }
        )
        NavigationDrawerItem(
            label = { Text("Help and feedback") },
            selected = false,
            icon = { Icon(Icons.AutoMirrored.Outlined.Help, contentDescription = null) },
            onClick = { /* Handle click */ },
        )
        Spacer(Modifier.height(12.dp))
    }
}