package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences as PrefsStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<PrefsStore> by preferencesDataStore(name = "rte_settings")

object Preferences {
    val RTE_ENABLED = booleanPreferencesKey("rte_enabled")
}
