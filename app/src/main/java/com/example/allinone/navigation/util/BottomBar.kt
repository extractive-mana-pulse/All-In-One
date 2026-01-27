package com.example.allinone.navigation.util

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.allinone.navigation.screen.HomeScreens

data class BottomNavigationItem<T:Any>(
    val title: String,
    val selectedIcon: Painter? = null,
    val unselectedIcon: Painter? = null,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val route : T
)

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    bottomBarState: MutableState<Boolean>,
    navController: NavHostController = rememberNavController()
) {
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = null/*painterResource(R.drawable.outline_home_24)*/,
            unselectedIcon = null/*painterResource(R.drawable.baseline_home_24)*/,
            hasNews = false,
            route = HomeScreens.Home
        ),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    LaunchedEffect(currentDestination) {
        val selectedIndex = items.indexOfFirst { it.route.toString() == currentDestination?.route }
        if (selectedIndex != -1) {
            selectedItemIndex = selectedIndex
        }
    }

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(durationMillis = 1200, easing = LinearOutSlowInEasing)),
        exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(durationMillis = 1200, easing = LinearOutSlowInEasing)),
        content = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
//                                    SslCertificate.saveState = true
                                }
                                launchSingleTop = true
//                                SslCertificate.restoreState = true
                            }
                        },
                        label = { Text(text = item.title) },
                        alwaysShowLabel = true,
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (item.badgeCount != null) {
                                        Badge { Text(text = item.badgeCount.toString()) }
                                    } else if (item.hasNews) {
                                        Badge()
                                    }
                                }
                            ) {
                                if (selectedItemIndex == index) item.selectedIcon else item.unselectedIcon?.let {
                                    Icon(
                                        painter = it,
                                        contentDescription = item.title
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    )
}