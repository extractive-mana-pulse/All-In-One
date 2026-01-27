package com.example.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.CourseDetails
import com.example.domain.repository.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val courseRepository: DetailsRepository
): ViewModel() {

    private val _courseDetails = MutableStateFlow<CourseDetails?>(null)
    val courseDetails: StateFlow<CourseDetails?> = _courseDetails

    fun loadCourse(id: Int) {
        viewModelScope.launch {
            val course = courseRepository.loadCourseById(id)
            _courseDetails.value = course
        }
    }
}