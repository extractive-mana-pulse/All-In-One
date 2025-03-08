package com.example.allinone.settings

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.R
import com.example.allinone.screens.Screens

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SettingScreen(
    navController: NavHostController = rememberNavController()
) {
    val context: Context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        SettingsItem(
            modifier = Modifier,
            title = "Compose article",
            description = "This page represents a compose article and you will learn how to make such screen using Jetpack compose.",
            icon = R.drawable.compose_logo,
            onClick = {
                navController.navigate(Screens.ComposeArticleScreen.route)
            }
        )
        SettingsItem(
            modifier = Modifier,
            title = "Quadrant",
            description = "In this section you will learn how to use compose wisely.",
            icon = R.drawable.compose_logo,
            onClick = {
                navController.navigate(Screens.Quadrant.route)
            }
        )
        SettingsItem(
            modifier = Modifier,
            title = "Tip Calculator",
            description = "In this codelab you will learn how to build a tip calculator app.",
            icon = R.drawable.compose_logo,
            onClick = {
                navController.navigate(Screens.TipCalculator.route)
            }
        )
        SettingsItem(
            title = "Art Space App",
            description = "In this section you will learn how to build an art space app.",
            icon = R.drawable.compose_logo,
            onClick = {
                navController.navigate(Screens.ArtSpace.route)
            }
        )
        SettingsItem(
            title = "Lemonade",
            description = "In this section you will learn how to build an art space app.",
            icon = R.drawable.compose_logo,
            onClick = {
                navController.navigate(Screens.Lemonade.route)
            }
        )
    }
}

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: Int,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth().clickable { onClick() },
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                ) },
            supportingContent = {
                Text(
                    text = description,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            },
            leadingContent = {
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            },
            trailingContent = { Text("") }
        )
        HorizontalDivider(
            modifier = Modifier.padding(start = 80.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    }
}