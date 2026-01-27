package com.example.presentation.art_space_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.presentation.R

@Composable
internal fun ArtImageLayout() {
    val currentIndex = remember { mutableIntStateOf(0) }
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

    CustomPointerButtons(
        currentIndex = currentIndex,
        imageCount = imageList.size
    )
}