package com.example.allinone.main.domain.model

data class Course(
    val id: Int,
    val title: String? = null,
    val subtitle: String? = null,
    val description: String? = null,
    val imageUrl: String? = null
)