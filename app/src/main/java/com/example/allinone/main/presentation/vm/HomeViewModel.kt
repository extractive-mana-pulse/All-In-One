package com.example.allinone.main.presentation.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.main.presentation.sealed.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

//@HiltViewModel
//class HomeViewModel @Inject constructor(
//    private val repository: CourseRepository
//): ViewModel() {
//
//    private val _state = MutableStateFlow<Resource<Course>>(Resource.Loading())
//    val state = _state.asStateFlow()
//
//    fun getCourseById(id: Int) {
//        viewModelScope.launch {
//            _state.value = Resource.Loading()
//            try {
//                val course = repository.getCourseById(id)
//                if (course != null) {
//                    _state.value = Resource.Success(course)
//                } else {
//                    _state.value = Resource.Error("Course not found")
//                }
//            } catch (e: Exception) {
//                _state.value = Resource.Error("Error: ${e.message}")
//            }
//        }
//    }
//}
//
//// Interface for the repository
//interface CourseRepository {
//    suspend fun getAllCourses(): List<Course>
//    suspend fun getCourseById(id: Int): Course?
//}
//
//// Repository implementation
//class CourseRepositoryImpl(private val context: Context) : CourseRepository {
//
//    override suspend fun getAllCourses(): List<Course> = withContext(Dispatchers.IO) {
//        loadCoursesFromJson(context) ?: emptyList()
//    }
//
//    override suspend fun getCourseById(id: Int): Course? = withContext(Dispatchers.IO) {
//        val courses = loadCoursesFromJson(context) ?: emptyList()
//        courses.find { it.id == id }
//    }
//
//    private fun loadCoursesFromJson(context: Context): List<Course>? {
//        return try {
//            // Open and read the JSON file from assets
//            val jsonString = context.assets.open("course_details.json").bufferedReader().use { it.readText() }
//
//            // Parse JSON using Gson
//            val gson = Gson()
//            val courseListType = object : TypeToken<List<Course>>() {}.type
//            gson.fromJson(jsonString, courseListType)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null // Return null in case of an error
//        }
//    }
//}
//
//object CourseRepositoryFactory {
//    fun create(context: Context): CourseRepository {
//        return CourseRepositoryImpl(context)
//    }
//}