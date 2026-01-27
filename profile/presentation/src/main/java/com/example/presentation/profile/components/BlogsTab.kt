package com.example.presentation.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.example.presentation.toastMessage

@Composable
internal fun BlogsTab(
    composition: LottieComposition?,
    progress: Float
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(282.dp)
        )
        Text(
            text = "Your blogs page is currently empty.",
        )
        TextButton(
            onClick = {
                // navController.navigate(ProfileScreens.PublishBlog.route)
                toastMessage(
                    context = context,
                    message = "this function is maintenance."
                )
            }
        ) {
            Text(
                text = "Do you want to publish a new blog?"
            )
        }
    }
}