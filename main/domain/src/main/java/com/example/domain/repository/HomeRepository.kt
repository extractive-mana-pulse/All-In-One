package com.example.domain.repository

import com.example.domain.model.Course
import com.example.domain.model.GithubCommands
import com.example.domain.model.Sections

interface HomeRepository {

    suspend fun loadCourses(): List<Course>?

    suspend fun loadSections(): List<Sections>?

    suspend fun loadGithubCommands(): List<GithubCommands>?
}
