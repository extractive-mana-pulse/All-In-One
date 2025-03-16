package com.example.allinone.main.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.R
import com.example.allinone.core.extension.toastMessage

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DetailsScreen(
    navController: NavHostController = rememberNavController()
) {
    // if user in this screen for more than 2-3 minutes pop up snackbar with switch to turn on read-mode
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        // here we could pass blog actual title
                        text = "Blog"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Navigate up to home screen"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            toastMessage(
                                context = context,
                                message = "Implement logic add to bookmarks"
                            )
                        }
                    ) {
                        // implement logic when item is already saved icon should be filled otherwise outlined.
                        // implement logic when item is already saved and once again bookmark is pressed,
                        // remove from bookmarks and update icon.
                        Icon(
                            Icons.Outlined.BookmarkBorder,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.compose_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onBackground)
                        .padding(16.dp)
                )
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "John Doe",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "·",
                            modifier = Modifier.padding(start = 12.dp),
                        )
                        TextButton(
                            onClick = { /* Handle Follow Action */ }
                        ) {
                            Text("Follow")
                        }
                    }
                    Text(
                        text = "Published · Oct, 28 2025",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            Text(
                text =
                    "I am John Doe, Co-Founder @ Outcome School, I have taught and mentored many developers, and their efforts landed them high-paying tech jobs, helped many tech companies in solving their unique problems, and created many open-source libraries being used by top companies. I am passionate about sharing knowledge through open-source, blogs, and videos.\n" +
                            "In this blog, we are going to learn how to remove duplicates from an array in Kotlin. As there are many ways to remove duplicates from an array in Kotlin, depending on the use case, we can decide which one to use.\n" +
                            "This article was originally published at Outcome School.\n" +
                            "We can use any function from the following to remove duplicates from an array in Kotlin:\n" +
                            "* distinct()\n" +
                            "* toSet()\n" +
                            "* toMutableSet()\n" +
                            "* toHashSet()\n" +
                            "Let’s start learning one by one by example.",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}