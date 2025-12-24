package com.example.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.allinone.R
import com.example.allinone.core.components.AppTopBar
import com.example.allinone.plcoding.mini_challenges.domain.model.months
import com.example.allinone.plcoding.mini_challenges.presentation.components.MonthsCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MiniChallengesScreen(
    onNavigateUpFromMiniChallengesScreen: () -> Unit = {},
    navigateToChallengeByString: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.mini_challenges),
                onNavigationClick = onNavigateUpFromMiniChallengesScreen
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(months) { month ->
                MonthsCard(
                    miniChallengeMonthDTO = month,
                    onChallengeClick = { id -> navigateToChallengeByString(id) }
                )
            }
        }
    }
}