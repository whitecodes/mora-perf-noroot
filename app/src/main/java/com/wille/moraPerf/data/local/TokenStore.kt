package com.wille.moraPerf.data.local

import android.content.Context
import androidx.core.content.edit

/**
 * Persists the user-entered API token in app-private storage.
 *
 * The daemon itself keeps this token in a plain file under `/data/adb/modules`,
 * so encrypting it here would add a dependency without changing the threat
 * model; it just must never be logged or committed.
 */
class TokenStore(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun read(): String? = prefs.getString(KEY_TOKEN, null)?.takeIf { it.isNotBlank() }

    fun save(token: String) = prefs.edit { putString(KEY_TOKEN, token) }

    fun clear() = prefs.edit { remove(KEY_TOKEN) }

    private companion object {
        const val PREFS_NAME = "mora_prefs"
        const val KEY_TOKEN = "api_token"
    }
}
