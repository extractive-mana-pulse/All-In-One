package com.example.allinone.main.domain.repository

import com.example.allinone.main.domain.model.CourseDetails

interface DetailsRepository {
    suspend fun loadCourseById(id: Int): CourseDetails?
}