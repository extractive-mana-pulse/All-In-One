package com.example.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.allinone.core.presentation.R

// create a page where it could be 2 layout. 1 list item, 2 vertical grid layout.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogsScreen(
    navigateToRichTextEditor: () -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Blogs",
                        )
                    },
                navigationIcon = {
                    IconButton(
                        onClick = { onNavigateUp() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = { navigateToRichTextEditor() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .align(Alignment.Center)
            ) {
                Text(
                    text = "Navigate to rich text editor blog"
                )
            }
        }
    }
}