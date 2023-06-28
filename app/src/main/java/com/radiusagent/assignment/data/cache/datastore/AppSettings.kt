package com.radiusagent.assignment.data.cache.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.radiusagent.assignment.domain.Security
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore("settings")

class AppSettings @Inject constructor(
    private val context: Context,
    private val security: Security,
) {
    fun getMasterKey(): ByteArray {
        val key = runBlocking { context.dataStore.data.first() }[MASTER_KEY]
        return if (key.isNullOrEmpty()) {
            val masterKey = security.generatePassphrase()
            runBlocking {
                context.dataStore.edit { preferences ->
                    preferences[MASTER_KEY] = masterKey.toString(Charsets.ISO_8859_1)
                }
            }
            masterKey
        } else {
            key.toByteArray(Charsets.ISO_8859_1)
        }
    }

    companion object {
        val MASTER_KEY = stringPreferencesKey("master_key")
    }
}