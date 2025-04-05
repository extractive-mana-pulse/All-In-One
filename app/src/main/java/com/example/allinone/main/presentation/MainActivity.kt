package com.example.allinone.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.main.presentation.vm.TimerViewModel
import com.example.allinone.navigation.navs.NavigationGraph
import com.example.allinone.settings.ThemePreferences
import com.example.allinone.settings.presentation.vm.ReadingViewModel
import com.example.allinone.settings.presentation.vm.ThemeViewModel
import com.example.allinone.ui.theme.AllInOneTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        enableEdgeToEdge()
        setContent {
            val viewModel = hiltViewModel<ReadingViewModel>()
            val scope = rememberCoroutineScope()
            val themePreferences = remember { ThemePreferences(applicationContext) }
            val isDarkTheme by viewModel.isDarkTheme.collectAsState()
            val isReadingTheme by viewModel.isReadingModeEnabled.collectAsState()
            val timerViewModel: TimerViewModel by viewModels()

            AllInOneTheme(
                darkTheme = isDarkTheme,
                readingTheme = isReadingTheme
            ) {
                val navController: NavHostController = rememberNavController()
                NavigationGraph(
                    navController = navController,
                    isDarkTheme = isDarkTheme,
                    timerViewModel = timerViewModel,
                    isReadingMode = isReadingTheme,
                    onThemeChanged = { newDarkTheme ->
                        viewModel.toggleDarkTheme(newDarkTheme)
                    },
                    fusedLocationClient = fusedLocationClient
                )
            }
        }
    }
}