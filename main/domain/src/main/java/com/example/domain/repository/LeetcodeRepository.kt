package com.example.domain.repository

import com.example.domain.model.Leetcode
import kotlinx.coroutines.flow.Flow

interface LeetcodeRepository {
    fun getAllAlgorithms(): Flow<List<Leetcode>>
    fun getLeetCodeById(id: String): Flow<Leetcode?>
    fun getLeetCodeByTitle(title: String): Flow<Leetcode?>
}