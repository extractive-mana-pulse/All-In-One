package com.example.allinone.codelabs.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.R
import com.example.allinone.core.components.AppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManager(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "",
                onNavigationClick = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.done),
                contentDescription = null
            )

            Text(
                text = stringResource(R.string.task_complete_title),
                modifier = Modifier.padding(0.dp, 24.dp, 0.dp, 8.dp),
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = stringResource(R.string.task_complete_title2),
                modifier = Modifier,
                fontSize = 16.sp
            )
        }
    }
}