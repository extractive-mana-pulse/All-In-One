package com.example.allinone.main.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SomeRepository {

    // build logic here.
    private val _someState = MutableStateFlow<SomeState>(SomeState.Loading)
    val someState = _someState.asStateFlow()

    suspend fun activateReadingMode() {
        _someState.value = SomeState.Success("Reading mode activated")

    }
}

sealed class SomeState {
    object Loading : SomeState()
    data class Success(val data: String) : SomeState()
    data class Error(val message: String) : SomeState()
}
