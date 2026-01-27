package com.example.presentation.deviceTemp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.presentation.autoNight.vm.TemperatureViewModel
import com.example.presentation.components.AppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllSensorsScreen(
    navController: NavHostController
) {
    val viewModel: TemperatureViewModel = hiltViewModel()
    val allSensorNames = viewModel.allSensorNames
    val tempData = viewModel.temperatureData
    val sensorManager = viewModel.sensorAvailable

    Scaffold(
        topBar = {
            AppTopBar(
                title = "All Device Sensors",
                onNavigationClick = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "All Available Sensors",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            if (!sensorManager) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No sensors found on this device",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            } else {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = allSensorNames,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Text(
                            text = "Type: $allSensorNames",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = String.format("%.1f°C", tempData),
                                fontSize = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

//                                val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
//                                Text(
//                                    text = "Last updated: ${dateFormat.format(Date(allSensorNames.lastUpdated))}",
//                                    fontSize = 12.sp,
//                                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                                )

                        Text(
                            text = "Accuracy: Low",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}