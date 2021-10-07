package com.gan.interbook.framework.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import net.openid.appauth.AuthState
import org.json.JSONException
import timber.log.Timber

class SharedUserPreferences(private val sharedPreferences: SharedPreferences) {
    fun saveAuthState(authState: AuthState?) {
        sharedPreferences.edit {
            putString(
                PreferencesKeys.AUTH_STATE,
                authState?.jsonSerializeString()
            )
        }
    }

    fun getAuthState(): AuthState? {
        val authState: String? = sharedPreferences.getString(PreferencesKeys.AUTH_STATE, null)
        authState?.let {
            try {
                return AuthState.jsonDeserialize(authState)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun saveUserId(userId: String) {
        sharedPreferences.edit {
            putString(
                PreferencesKeys.USER_ID,
                userId
            )
        }
    }

    fun getUserId(): String =
        sharedPreferences.getString(PreferencesKeys.USER_ID, null) ?: ""

    fun saveUsername(username: String) {
        sharedPreferences.edit {
            putString(
                PreferencesKeys.USER_NAME,
                username
            )
        }
    }

    fun getUsername(): String =
        sharedPreferences.getString(PreferencesKeys.USER_NAME, null) ?: ""

    fun clean() {
        sharedPreferences.edit { clear() }
    }
}