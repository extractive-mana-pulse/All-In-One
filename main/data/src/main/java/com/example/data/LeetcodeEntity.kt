package com.example.data

import com.example.domain.model.Leetcode

data class LeetcodeEntity(
    val title: String = "",
    val difficulty: String = "",
    val category: String = "",
    val description: String = "",
    val examples: String = "",
    val hint: String = "",
    val leetcode_url: String = "",
    val publishedDate: String = "",
    val author: String = ""
) {
    fun toLeetcode(id: String): Leetcode = Leetcode(
        id = id,
        title = title,
        difficulty = difficulty,
        category = category,
        description = description,
        examples = examples,
        hint = hint,
        leetcodeUrl = leetcode_url,
        publishedDate = publishedDate,
        author = author
    )
}