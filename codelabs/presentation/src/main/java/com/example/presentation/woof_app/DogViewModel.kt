package com.example.presentation.woof_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presentation.woof_app.Dog
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class DogViewModel : ViewModel() {
    val dogs: StateFlow<List<Dog>> = flowOf(com.example.presentation.woof_app.dogs).stateIn(
        viewModelScope, SharingStarted.Companion.Lazily, emptyList())
}