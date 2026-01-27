package com.example.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.allinone.core.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    onNavigationClick: () -> Unit,
    onActionClick: @Composable () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigationClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_arrow_back_24),
                    contentDescription = null
                )
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                onActionClick()
            }
        },
        scrollBehavior = scrollBehavior
    )
}