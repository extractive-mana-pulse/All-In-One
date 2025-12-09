package com.example.allinone.codelabs.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.R
import com.example.allinone.core.components.AppTopBar
import com.example.allinone.core.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArtSpace(
    navController: NavHostController = rememberNavController()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Art Space",
                onNavigationClick = { navController.navigateUp() },
                scrollBehavior = scrollBehavior
            )
        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .safeDrawingPadding()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ArtImageLayout()
        }
    }
}

@Composable
fun ArtImageLayout() {
    val currentIndex = remember { mutableIntStateOf(0) } // Start with the first image
    val artTitle = remember { mutableStateOf("") }
    val artAuthor = remember { mutableStateOf("") }
    val artYear = remember { mutableStateOf("") }

    val imageList = listOf(
        R.drawable.star_sky,
        R.drawable.blue_rose,
        R.drawable.avenue_in_the_rain
    )


    artTitle.value = when (currentIndex.intValue) {
        0 -> stringResource(R.string.star_sky_title)
        1 -> stringResource(R.string.blue_rose_title)
        2 -> stringResource(R.string.avenue_in_the_rain_title)
        else -> ""
    }

    artAuthor.value = when (currentIndex.intValue) {
        0 -> stringResource(R.string.star_sky_author)
        1 -> stringResource(R.string.blue_rose_author)
        2 -> stringResource(R.string.avenue_in_the_rain_author)
        else -> ""
    }

    artYear.value = when (currentIndex.intValue) {
        0 -> stringResource(R.string.star_sky_year)
        1 -> stringResource(R.string.blue_rose_year)
        2 -> stringResource(R.string.avenue_in_the_rain_year)
        else -> ""
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(1.dp), clip = true, spotColor = MaterialTheme.colorScheme.outline),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(imageList[currentIndex.intValue]),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(36.dp)
                .background(MaterialTheme.colorScheme.background)
        )
    }
    ArtInformationLayout(
        title = artTitle.value,
        author = artAuthor.value,
        year = "(${artYear.value})"
    )

    PointerButtons(
        currentIndex = currentIndex,
        imageCount = imageList.size
    )
}

@Composable
fun ArtInformationLayout(title: String, author: String, year: String) {

    Column(
        modifier = Modifier
            .padding(top = 40.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Start,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(bottom = 8.dp, start = 16.dp, end = 16.dp, top = 16.dp)
        )
        Row {
            Text(
                text = author,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 16.dp, start = 16.dp)
            )
            Text(
                text = year,
                fontWeight = FontWeight.Thin,
                modifier = Modifier.padding(bottom = 16.dp, start = 4.dp, end = 16.dp)
            )
        }
    }
}

@Composable
fun PointerButtons(currentIndex: MutableState<Int>, imageCount: Int) {
    Row(
        modifier = Modifier
            .padding(top = 40.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,
    ) {
        PrimaryButton(
            onClick = {
                if (currentIndex.value > 0) {
                    currentIndex.value -= 1
                }
            },
            modifier = Modifier.weight(0.5f).padding(end = 24.dp)
        ) {
            Text(text = "Previous")
        }
        PrimaryButton(
            onClick = {
                if (currentIndex.value < imageCount - 1) {
                    currentIndex.value += 1
                }
            },
            modifier = Modifier.weight(0.5f).padding(start = 24.dp)
        ) {
            Text(text = "Next")
        }
    }
}