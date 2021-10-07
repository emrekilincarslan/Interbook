package com.gan.dependencies

object Dependencies {
    const val kotlin_standard_library = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    const val ktx = "androidx.core:core-ktx:${Versions.ktx}"
    const val kotlin_coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines_version}"
    const val kotlin_coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_version}"
    const val kotlin_coroutines_play_services =
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines_play_services}"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}"
    const val activity_ktx = "androidx.activity:activity-ktx:${Versions.activity_ktx}"
    const val navigation_fragment_ktx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.nav_components}"
    const val navigation_runtime =
        "androidx.navigation:navigation-runtime:${Versions.nav_components}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.nav_components}"
    const val material_dialogs = "com.afollestad.material-dialogs:core:${Versions.material_dialogs}"
    const val material_dialogs_lifecycles =
        "com.afollestad.material-dialogs:lifecycle:${Versions.material_dialogs}"
    const val material_dialogs_date =
        "com.afollestad.material-dialogs:datetime:${Versions.material_dialogs}"
    const val material_dialogs_bottomsheets =
        "com.afollestad.material-dialogs:bottomsheets:${Versions.material_dialogs}"
    const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"
    const val play_core = "com.google.android.play:core:${Versions.play_core}"
    const val lifecycle_runtime =
        "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle_version}"
    const val lifecycle_java8 =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle_version}"
    const val lifecycle_coroutines =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit2_version}"
    const val retrofit_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2_version}"
    const val markdown_processor = "com.yydcdut:markdown-processor:${Versions.markdown_processor}"
    const val multindex = "androidx.multidex:multidex:${Versions.multi_index}"
    const val app_auth = "com.github.agologan:AppAuth-Android:${Versions.app_auth}"
    const val hilt_dagger = "com.google.dagger:hilt-android:${Versions.hilt_version}"
    const val hilt_viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt_viewmodel}"
    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    const val security = "androidx.security:security-crypto:${Versions.security}"
    const val back_fragment = "net.skoumal.fragmentback:fragment-back:${Versions.back_fragment}"
    const val permission_dispatcher =
        "org.permissionsdispatcher:permissionsdispatcher:${Versions.permission_dispatcher}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.squareup_okhttp}"
    const val logging_interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.squareup_okhttp}"
    const val okio = "com.squareup.okio:okio:${Versions.okio}"
    const val dialogs_date_time =
        "com.afollestad.material-dialogs:datetime:${Versions.material_dialogs}"
    const val play_services_auth =
        "com.google.android.gms:play-services-auth:${Versions.play_services_auth}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.version_glide}"
    const val play_services_maps =
        "com.google.android.gms:play-services-maps:${Versions.play_services_maps}"

    const val google_api_client ="com.google.api-client:google-api-client:${Versions.google_api_client}"
    const val google_api_client_android ="com.google.api-client:google-api-client-android:${Versions.google_api_client}"
    const val google_api_client_people ="com.google.apis:google-api-services-people:${Versions.google_api_client_people}"
    const val map_utils = "com.google.maps.android:android-maps-utils:${Versions.map_utils}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber_version}"
    const val spanny = "com.binaryfork:spanny:${Versions.spanny_version}"

    const val firebase_bom = "com.google.firebase:firebase-bom:${Versions.firebase_bom}"
    const val firebase_analytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebase_crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
}