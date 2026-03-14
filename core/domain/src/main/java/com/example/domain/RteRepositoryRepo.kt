package com.example.domain

import kotlinx.coroutines.flow.Flow

interface RteRepositoryRepo {
    val isRteEnabled: Flow<Boolean>
    suspend fun setRteEnabled(enabled: Boolean)
}