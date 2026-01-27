package com.example.data.repositoryImpl

import android.content.Context
import android.util.Log
import com.example.domain.model.Course
import com.example.domain.model.GithubCommands
import com.example.domain.model.Sections
import com.example.domain.repository.HomeRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeRepositoryImpl(
    private val context: Context
) : HomeRepository {

    override suspend fun loadCourses(): List<Course>? {
        return try {
            val jsonString = context.assets.open("course.json").bufferedReader().use { it.readText() }
            val gson = Gson()
            val courseType = object : TypeToken<List<Course>>() {}.type
            gson.fromJson(jsonString, courseType)
        } catch (e: Exception) {
            Log.e("CourseRepository", "Error loading courses: ${e.message}", e)
            null
        }
    }

    override suspend fun loadSections(): List<Sections>? {
        try {
            val jsonString = context.assets.open("sections.json").bufferedReader().use { it.readText() }

            val gson = Gson()
            val courseType = object : TypeToken<List<Sections>>() {}.type
            return gson.fromJson(jsonString, courseType)
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    override suspend fun loadGithubCommands(): List<GithubCommands>? {
        try {
            val jsonString = context.assets.open("github_commands.json").bufferedReader().use { it.readText() }
            val gson = Gson()
            val courseType = object : TypeToken<List<GithubCommands>>() {}.type
            return gson.fromJson(jsonString, courseType)
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }
}