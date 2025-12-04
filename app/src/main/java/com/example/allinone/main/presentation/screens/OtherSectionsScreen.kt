package com.example.allinone.main.presentation.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.R
import com.example.allinone.main.domain.model.GithubCommands
import com.example.allinone.main.domain.model.Sections
import com.example.allinone.main.presentation.vm.HomeViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectionScreen(
    navController: NavHostController = rememberNavController(),
    id: Int
) {
    val context = LocalContext.current
    val homeViewModel: HomeViewModel = hiltViewModel()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val loadGithubCommands by homeViewModel.githubCommands.collectAsStateWithLifecycle()
    val sectionsDetails = remember { mutableStateOf(loadSectionsByIdFromJson(context = context, id = id)) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = sectionsDetails.value?.name ?: "Section"
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
                            contentDescription = stringResource(R.string.from_other_sections_to_somewhere)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(sectionsDetails.value?.name) {
                "Github commands" -> {
                    items(loadGithubCommands?.size ?: 0) { command ->
                        CommandItem(commands = loadGithubCommands!![command])
                    }
                }
            }
        }
    }
}

@Composable
fun CommandItem(commands: GithubCommands) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = commands.commands ?: "",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = commands.description ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun SectionScreenPreview() {
    SectionScreen(id = 1)
}

private fun loadSectionsByIdFromJson(context: Context, id: Int): Sections? {
    return try {
        val jsonString = context.assets.open("sections.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val listType = object : TypeToken<List<Sections>>() {}.type
        val sectionsList: List<Sections> = gson.fromJson(jsonString, listType)
        sectionsList.find { it.id == id }
    } catch (e: Exception) {
        Log.d("DetailsScreen", "Error loading course details: ${e.message}")
        null
    }
}