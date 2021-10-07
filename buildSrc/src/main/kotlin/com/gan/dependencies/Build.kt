package com.gan.dependencies

object Build {
    const val build_tools = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val google_services = "com.google.gms:google-services:${Versions.play_services}"
    const val crashlytics_gradle =
        "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlytics_gradle}"
    const val safe_args_gradle =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.safe_args}"
    const val hilt_gradle = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_version}"
    const val firebase_distribution =
        "com.google.firebase:firebase-appdistribution-gradle:${Versions.firebase_distribution}"
}
