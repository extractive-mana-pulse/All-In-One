package com.example.allinone.codelabs.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.codelabs.domain.model.Dog
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class DogViewModel : ViewModel() {
    val dogs: StateFlow<List<Dog>> = flowOf(com.example.allinone.codelabs.domain.model.dogs).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}