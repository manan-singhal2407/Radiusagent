package com.radiusagent.assignment.data.cache.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("home_settings")

class HomeSettings @Inject constructor(private val context: Context) {

    fun getHomeDataLastLoadDate(): String? {
        val preferenceKey = stringPreferencesKey(homeDataLastLoaded)
        return runBlocking { context.dataStore.data.first() }[preferenceKey]
    }

    fun saveHomeDataLastLoadDate(date: String) {
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[stringPreferencesKey(homeDataLastLoaded)] = date
            }
        }
    }

    companion object {
        const val homeDataLastLoaded = "home_data_last_loaded"
    }
}