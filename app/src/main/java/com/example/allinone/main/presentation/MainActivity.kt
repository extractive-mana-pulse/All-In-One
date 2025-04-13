package com.example.allinone.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.main.presentation.vm.MainViewModel
import com.example.allinone.main.presentation.vm.TimerViewModel
import com.example.allinone.navigation.navs.NavigationGraph
import com.example.allinone.settings.presentation.vm.DarkThemeViewModel
import com.example.allinone.settings.presentation.vm.ReadingModeViewModel
import com.example.allinone.ui.theme.AllInOneTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel = viewModels<MainViewModel>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !mainViewModel.value.isReady.value
            }
        }
        enableEdgeToEdge()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            val timerViewModel: TimerViewModel by viewModels()
            val readingModeViewModel = hiltViewModel<ReadingModeViewModel>()
            val darkThemeViewModel = hiltViewModel<DarkThemeViewModel>()
            val isDarkTheme by darkThemeViewModel.isDarkTheme.collectAsStateWithLifecycle()
            val isReadingTheme by readingModeViewModel.isReadingModeEnabled.collectAsStateWithLifecycle()

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
                        darkThemeViewModel.toggleDarkTheme(newDarkTheme)
                    },
                    fusedLocationClient = fusedLocationClient
                )
            }
        }
    }
}