package com.example.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.domain.RteRepositoryRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RteRepositoryImpl(private val context: Context): RteRepositoryRepo {

    override val isRteEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[Preferences.RTE_ENABLED] ?: false
        }

    override suspend fun setRteEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[Preferences.RTE_ENABLED] = enabled
        }
    }
}