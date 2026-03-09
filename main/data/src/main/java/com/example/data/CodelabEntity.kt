package com.example.data

import com.example.domain.model.Codelab

data class CodelabEntity(
    val title: String = "",
    val subtitle: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val web_url: String = "",
    val publishedDate: String = "",
    val author: String = ""
) {
    fun toCodelab(id: String): Codelab = Codelab(
        id = id,
        title = title,
        subtitle = subtitle,
        description = description,
        imageUrl = imageUrl,
        webUrl = web_url,
        publishedDate = publishedDate,
        author = author
    )
}