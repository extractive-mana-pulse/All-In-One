package com.example.allinone.main.domain.model

data class CourseDetails (
    val id: Int,
    val title: String? = null,
    val subtitle: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val publishedDate: String? = null,
    val author: String? = null,
)