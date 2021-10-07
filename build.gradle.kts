// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(com.gan.dependencies.Build.build_tools)
        classpath(com.gan.dependencies.Build.kotlin_gradle_plugin)
        classpath(com.gan.dependencies.Build.hilt_gradle)
        classpath(com.gan.dependencies.Build.google_services)
        classpath(com.gan.dependencies.Build.crashlytics_gradle)
        classpath(com.gan.dependencies.Build.firebase_distribution)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://www.jitpack.io")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}