package com.example.presentation.home

import com.example.domain.model.Codelab

data class HomeUiState(
    val codelabs: List<Codelab> = emptyList(),
    val codelabsFiltered: List<Codelab> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val error: String? = null
)