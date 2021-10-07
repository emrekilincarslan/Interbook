package com.gan.interbook.framework.preferences

import com.gan.interbook.BuildConfig

object PreferencesKeys {
    const val USER_PREFERENCES_FILE = BuildConfig.APPLICATION_ID + ".user"

    const val AUTH_STATE = BuildConfig.APPLICATION_ID + ".AuthState"
    const val USER_ID = BuildConfig.APPLICATION_ID + ".UserId"
    const val USER_NAME = BuildConfig.APPLICATION_ID + ".Username"
}