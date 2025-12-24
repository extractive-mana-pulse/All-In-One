package com.example.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.main.domain.model.Course
import com.example.allinone.main.domain.model.GithubCommands
import com.example.allinone.main.domain.model.Sections
import com.example.allinone.main.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _course = MutableStateFlow<List<Course>?>(null)
    val course: StateFlow<List<Course>?> = _course
        .onStart { loadCourse() }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    private val _sections = MutableStateFlow<List<Sections>?>(null)
    val sections: StateFlow<List<Sections>?> = _sections
        .onStart { loadSections() }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    private val _githubCommands = MutableStateFlow<List<GithubCommands>?>(null)
    val githubCommands = _githubCommands
        .onStart { loadGithubCommands() }
        .stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null
    )

    private fun loadGithubCommands() {
        viewModelScope.launch {
            val githubCommands = homeRepository.loadGithubCommands()
            _githubCommands.value = githubCommands
        }
    }

    private fun loadCourse() {
        viewModelScope.launch {
            val courses = homeRepository.loadCourses()
            _course.value = courses
        }
    }

    private fun loadSections() {
        viewModelScope.launch {
            val sections = homeRepository.loadSections()
            _sections.value = sections
        }
    }
}