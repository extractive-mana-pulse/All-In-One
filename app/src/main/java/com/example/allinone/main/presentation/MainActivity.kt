package com.example.allinone.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.navigation.NavigationGraph
import com.example.allinone.settings.ThemePreferences
import com.example.allinone.ui.theme.AllInOneTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val scope = rememberCoroutineScope()
            val themePreferences = remember { ThemePreferences(applicationContext) }
            val isDarkTheme by themePreferences.isDarkTheme.collectAsState(initial = false)
            AllInOneTheme(
                darkTheme = isDarkTheme
            ) {
                val navController: NavHostController = rememberNavController()
                NavigationGraph(
                    navController = navController,
                    isDarkTheme = isDarkTheme,
                    onThemeChanged = { newTheme ->
                        scope.launch {
                            themePreferences.saveThemePreference(newTheme)
                        }
                    }
                )
            }
        }
    }
}