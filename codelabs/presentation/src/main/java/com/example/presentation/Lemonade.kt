package com.example.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Lemonade(
    onNavigateUp: () -> Unit = {},
) {
    var result by remember { mutableIntStateOf(1) }
    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = "Lemonade")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onNavigateUp() }
                    ) {
                        Icon(
                            painter = painterResource(com.example.data.R.drawable.outline_arrow_back_24),
                            contentDescription = stringResource(R.string.from_lemonade_to_somewhere)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { value ->
        Column(
            modifier = Modifier.fillMaxSize().padding(value).verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val imageResource = when (result) {
                1 -> R.drawable.lemon_tree
                2 -> R.drawable.lemon_squeeze
                3 -> R.drawable.lemon_drink
                4 -> R.drawable.lemon_restart
                else -> {
                    R.drawable.lemon_tree
                }
            }

            val stringResource = when (result) {
                1 -> R.string.lemon_tree
                2 -> R.string.lemon_squeeze
                3 -> R.string.lemon_drink
                4 -> R.string.lemon_restart
                else -> {
                    R.string.lemon_tree
                }
            }

            Image(
                painter = painterResource(imageResource),
                contentDescription = null,
                Modifier.clickable {
                    result.plus(1)
                    if (result==4) {
                        result = 1
                    } else {
                        result++
                    }
                }
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = stringResource(stringResource)
            )
        }
    }
}