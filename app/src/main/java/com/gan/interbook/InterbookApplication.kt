package com.gan.interbook

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import com.gan.interbook.BuildConfig
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

import timber.log.Timber.DebugTree


@HiltAndroidApp
class InterbookApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }
}

private class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, @NonNull message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        FirebaseCrashlytics.getInstance().log(message)
        if (t != null) {
            FirebaseCrashlytics.getInstance().recordException(t)
        }
    }
}