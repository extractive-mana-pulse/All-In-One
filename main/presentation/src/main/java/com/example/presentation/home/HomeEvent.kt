package com.example.presentation.home

sealed interface HomeEvent {
    data class Error(val message: String) : HomeEvent
}