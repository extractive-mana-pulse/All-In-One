package com.example.data.repositoryImpl

import android.content.Context
import android.util.Log
import com.example.domain.model.CourseDetails
import com.example.domain.repository.DetailsRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailsRepositoryImpl(
    private val context: Context
): DetailsRepository {

    override suspend fun loadCourseById(id: Int): CourseDetails? {
        return try {
            val jsonString = context.assets.open("course_details.json").bufferedReader().use { it.readText() }
            val gson = Gson()
            val listType = object : TypeToken<List<CourseDetails>>() {}.type
            val coursesList: List<CourseDetails> = gson.fromJson(jsonString, listType)
            coursesList.find { it.id == id }
        } catch (e: Exception) {
            Log.d("CourseRepository", "Error loading course details: ${e.message}")
            null
        }
    }
}