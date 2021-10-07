package com.gan.dependencies

object AnnotationProcessing {
    const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    const val lifecycle_compiler =
        "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle_version}"
    const val hilt_dagger_compiler =
        "com.google.dagger:hilt-android-compiler:${Versions.hilt_version}"
    const val hilt_viewmodel_compiler = "androidx.hilt:hilt-compiler:${Versions.hilt_viewmodel}"
    const val permission_dispatcher_processor =
        "org.permissionsdispatcher:permissionsdispatcher-processor:${Versions.permission_dispatcher}"
    const val glide_annotation = "com.github.bumptech.glide:compiler:${Versions.version_glide}"
}