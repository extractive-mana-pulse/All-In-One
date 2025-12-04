package com.example.allinone.main.domain.repository

import com.example.allinone.main.domain.model.Course
import com.example.allinone.main.domain.model.GithubCommands
import com.example.allinone.main.domain.model.Sections

interface HomeRepository {

    suspend fun loadCourses(): List<Course>?

    suspend fun loadSections(): List<Sections>?

    suspend fun loadGithubCommands(): List<GithubCommands>?
}
