package com.example.domain.repository

import com.example.domain.model.Codelab
import kotlinx.coroutines.flow.Flow

interface CodelabsRepository {
    fun getAllCodelabs(): Flow<List<Codelab>>
    fun getCodelabById(id: String): Flow<Codelab?>
    fun getCodelabByTitle(title: String): Flow<Codelab?>
}