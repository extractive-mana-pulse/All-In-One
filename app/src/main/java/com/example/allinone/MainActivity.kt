package com.example.allinone

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
import com.example.allinone.navigation.navs.NavigationGraph
import com.example.allinone.settings.autoNight.domain.model.Twilight
import com.example.allinone.settings.autoNight.presentation.vm.AutoNightViewModel
import com.example.allinone.settings.autoNight.presentation.vm.DarkThemeViewModel
import com.example.allinone.settings.autoNight.presentation.vm.ScheduledModeViewModel
import com.example.allinone.settings.readingMode.presentation.vm.ReadingModeViewModel
import com.example.allinone.ui.theme.AllInOneTheme
import com.example.data.firebase.AuthenticationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.LocalTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel = viewModels<MainViewModel>()
    private lateinit var authenticationManager: AuthenticationManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !mainViewModel.value.isReady.value
            }
        }
        enableEdgeToEdge()
        authenticationManager = AuthenticationManager(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            val darkThemeViewModel : DarkThemeViewModel = hiltViewModel()
            val autoNightModeViewModel: AutoNightViewModel = hiltViewModel()
            val readingModeViewModel : ReadingModeViewModel = hiltViewModel()
            val scheduledModeViewModel : ScheduledModeViewModel = hiltViewModel()
            val isDarkTheme by darkThemeViewModel.isDarkTheme.collectAsStateWithLifecycle()
            val twilightState by autoNightModeViewModel.twilight.collectAsStateWithLifecycle()
            val isScheduledMode by scheduledModeViewModel.isScheduledMode.collectAsStateWithLifecycle()
            val isReadingTheme by readingModeViewModel.isReadingModeEnabled.collectAsStateWithLifecycle()

            AllInOneTheme(
                darkTheme = isDarkTheme,
                readingTheme = isReadingTheme
            ) {
                val navController: NavHostController = rememberNavController()

                NavigationGraph(
                    navController = navController,
                    isDarkTheme = isDarkTheme,
                    isReadingMode = isReadingTheme,
                    onThemeChanged = { newDarkTheme ->
                        darkThemeViewModel.toggleDarkTheme(newDarkTheme)
                    },
                    fusedLocationClient = fusedLocationClient,
                    context = applicationContext,
                    authenticationManager = authenticationManager,
                    scheduleToggleState = isScheduledMode
                )
            }
            // check when sunset, it should automatically change the theme of the app.
            scheduledMode(
                isScheduledModeSelected = isScheduledMode,
                state = twilightState,
                isDarkTheme = isDarkTheme,
                onThemeChanged = { newDarkTheme ->
                    darkThemeViewModel.toggleDarkTheme(newDarkTheme)
                }
            )
            // write a function. is scheduled mode is selected.
            // use logic -> take current time and determine if it's day time or night time.
            // depending on that change theme of the app. (This logic already implemented in screen ScheduledModeScreen.kt)
            // just wrap it correctly and implement it in main activity.
        }
    }
    private fun scheduledMode(
        isScheduledModeSelected: Boolean,
        state: Twilight,
        isDarkTheme: Boolean,
        onThemeChanged: (Boolean) -> Unit
    ) {
        if (isScheduledModeSelected) {
            val currentDateTime: LocalDateTime = LocalDateTime.now()
            val currentLocalTime = currentDateTime.toLocalTime()

            val sunriseString = state.results.sunrise
            val sunsetString = state.results.sunset

            if (sunriseString != null && sunsetString != null) {

                val sunriseTime = LocalTime.parse(sunriseString)
                val sunsetTime = LocalTime.parse(sunsetString)

                val isDayTime = currentLocalTime.isAfter(sunriseTime) && currentLocalTime.isBefore(sunsetTime)
                if (isDayTime && isDarkTheme) onThemeChanged(false) else if (!isDayTime && !isDarkTheme) onThemeChanged(true)
            }
        }
    }
}