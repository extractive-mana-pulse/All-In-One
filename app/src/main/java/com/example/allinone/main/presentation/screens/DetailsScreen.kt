package com.example.allinone.main.presentation.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.allinone.R
import com.example.allinone.core.extension.toastMessage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavHostController = rememberNavController(),
    id: Int,
) {
    val context = LocalContext.current
    val courseDetails by remember { mutableStateOf(loadCourseByIdFromJson(context, id)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = courseDetails?.title ?: "Blog"
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
                                message = "In progress. This feature will be implemented soon"
                            )
                        }
                    ) {
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
                .padding(16.dp)
        ) {
            if (courseDetails != null) {
                DetailsItem(course = courseDetails!!)
            } else {
                Text(
                    text = "Course details not found, Please check the ID and try again",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                Button(
                    onClick = {
                        // Handle retry logic here
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Retry"
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailsItem(course: CourseDetails) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            model = course.imageUrl ?: R.drawable.compose_logo,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RectangleShape)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = course.author ?: "John Doe",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            TextButton(
                onClick = {
                    toastMessage(
                        context = context,
                        message = "In progress. This feature will be implemented soon"
                    )
                }
            ) {
                Text("Follow")
            }
        }
        Text(
            text = course.publishedDate ?: "Published · Oct, 28 2025",
            fontSize = 12.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = course.description ?: "No description available",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

private fun loadCourseByIdFromJson(context: Context, id: Int): CourseDetails? {
    return try {
        val jsonString = context.assets.open("course_details.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val listType = object : TypeToken<List<CourseDetails>>() {}.type
        val coursesList: List<CourseDetails> = gson.fromJson(jsonString, listType)
        coursesList.find { it.id == id }
    } catch (e: Exception) {
        Log.d("DetailsScreen", "Error loading course details: ${e.message}")
        null
    }
}

data class CourseDetails (
    val id: Int,
    val title: String? = null,
    val subtitle: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val publishedDate: String? = null,
    val author: String? = null,
)