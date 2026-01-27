package com.example.domain.repository

import com.example.domain.model.CourseDetails

interface DetailsRepository {
    suspend fun loadCourseById(id: Int): CourseDetails?
}