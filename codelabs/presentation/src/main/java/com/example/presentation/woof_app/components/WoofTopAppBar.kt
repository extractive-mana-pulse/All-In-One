package com.example.presentation.woof_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.allinone.codelabs.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WoofTopAppBar(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = {},
) {

    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(8.dp),
                    painter = painterResource(R.drawable.if_woof_logo),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.woof),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onNavigateUp()
                }
            ) {
                Icon(
                    painter = painterResource(com.example.allinone.core.presentation.R.drawable.outline_arrow_back_24),
                    contentDescription = null
                )
            }
        },
        modifier = modifier,
    )
}
