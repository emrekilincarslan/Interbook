import com.gan.applications.Application
import com.gan.applications.GbooksApplication
import com.gan.dependencies.*
import java.io.FileInputStream
import java.io.IOException
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.appdistribution")
}

val keystoreProperties = Properties()
try {
    val keystorePropertiesFile = rootProject.file("keystore.properties")
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
} catch (e: IOException) {
    // We don"t have release keys, ignoring - this will allow us to build for Debug without the need of release keys
}

fun getVersionCode(fallbackValue: Int): Int {
    return if (project.hasProperty("vCode"))
        project.property("vCode").toString().toInt()
    else
        fallbackValue
}

android {
    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion(Versions.buildSdk)

    defaultConfig {
        applicationId(Application.internal_app_id)
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        val keyStorePath = keystoreProperties["STORE_FILE"] ?: return@signingConfigs

        val keyStoreFile = rootProject.file(keyStorePath.toString())
        val keyStorePassword = keystoreProperties["STORE_PASSWORD"].toString()
        val alias = keystoreProperties["KEY_ALIAS"].toString()
        val aliasPassword = keystoreProperties["KEY_PASSWORD"].toString()

        getByName("debug") {
            storeFile = keyStoreFile
            storePassword = keyStorePassword
            keyAlias = alias
            keyPassword = aliasPassword
        }
    }

    val stringType = "String"
    val longType = "Long"

    buildTypes {
        val scope: String by project
        val responseType: String by project
        val registrationParam: String by project

        buildTypes.forEach {
            it.buildConfigField(stringType, "SCOPE", scope)
            it.buildConfigField(stringType, "RESPONSE_TYPE", responseType)
            it.buildConfigField(stringType, "REGISTER_PARAM", registrationParam)
        }

        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            val baseUrl: String by project
            val endSession: String by project
            val tokenEndpointUri: String by project
            val authorizationEndpointUri: String by project

            buildConfigField(stringType, "BASE_URL", baseUrl)
            buildConfigField(stringType, "END_SESSION", endSession)
            buildConfigField(stringType, "TOKEN_END_POINT_URI", tokenEndpointUri)
            buildConfigField(stringType, "AUTHORIZATION_END_POINT_URI", authorizationEndpointUri)
        }

        create("acc") {
            initWith(getByName("debug"))
            isDebuggable = false
            isMinifyEnabled = false

            versionNameSuffix = "-acc"
            applicationIdSuffix = ".acc"

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            val baseUrlAcc: String by project
            val endSessionAcc: String by project
            val tokenEndpointUriAcc: String by project
            val authorizationEndpointUriAcc: String by project

            buildConfigField(stringType, "BASE_URL", baseUrlAcc)
            buildConfigField(stringType, "END_SESSION", endSessionAcc)
            buildConfigField(stringType, "TOKEN_END_POINT_URI", tokenEndpointUriAcc)
            buildConfigField(
                stringType,
                "AUTHORIZATION_END_POINT_URI",
                authorizationEndpointUriAcc
            )
        }

        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false

            versionNameSuffix = "-test"
            applicationIdSuffix = ".test"

            val baseUrlTest: String by project
            val endSessionTest: String by project
            val tokenEndpointUriTest: String by project
            val authorizationEndpointUriTest: String by project

            buildConfigField(stringType, "BASE_URL", baseUrlTest)
            buildConfigField(stringType, "END_SESSION", endSessionTest)
            buildConfigField(stringType, "TOKEN_END_POINT_URI", tokenEndpointUriTest)
            buildConfigField(
                stringType,
                "AUTHORIZATION_END_POINT_URI",
                authorizationEndpointUriTest
            )

            firebaseAppDistribution {
                serviceCredentialsFile = "service_auth.json"
                groups = "ganteam"
            }
        }
    }

    flavorDimensions("app")
    productFlavors {
        create("gbooks") {
            dimension = "app"
            applicationIdSuffix = ".gbooks"

            versionCode =
                getVersionCode(GbooksApplication.version_code)
            versionName = GbooksApplication.version_name

            val clientIdGbooks: String by project
            val signOutUri: String by project
            val redirectUri: String by project
            val apiKey: String by project

            buildConfigField(stringType, "CLIENT_ID", clientIdGbooks)
            buildConfigField(stringType, "SIGN_OUT_URI", signOutUri)
            buildConfigField(stringType, "REDIRECT_URI", redirectUri)
            buildConfigField(stringType, "API_KEY", apiKey)
        }
    }

    compileOptions {
        sourceCompatibility(Versions.java_version)
        targetCompatibility(Versions.java_version)
    }

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).configureEach {
        kotlinOptions {
            jvmTarget = Versions.java_version.toString()
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(Dependencies.kotlin_standard_library)
    implementation(Dependencies.ktx)
    implementation(Dependencies.activity_ktx)
    implementation(SupportDependencies.appcompat)
    implementation(SupportDependencies.constraintlayout)
    implementation(SupportDependencies.material_design)
    implementation(Dependencies.hilt_dagger)
    implementation(Dependencies.hilt_viewmodel)
    implementation(Dependencies.permission_dispatcher)
    implementation(Dependencies.navigation_ui)
    implementation(Dependencies.navigation_fragment_ktx)
    implementation(Dependencies.timber)
    implementation(Dependencies.app_auth)
    implementation(Dependencies.okhttp)
    implementation(Dependencies.logging_interceptor)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofit_gson)
    implementation(Dependencies.material_dialogs)
    implementation(Dependencies.material_dialogs_lifecycles)
    implementation(Dependencies.material_dialogs_date)
    implementation(Dependencies.permission_dispatcher)
    implementation(Dependencies.spanny)
    implementation(SupportDependencies.swipe_refresh_layout)
    implementation(Dependencies.play_services_maps)

    //OAUTH2
    implementation(Dependencies.play_services_auth)

    //glide
    implementation(Dependencies.glide)

    // Firebase
    implementation(platform(Dependencies.firebase_bom))
//    implementation(Dependencies.firebase_analytics)
    implementation(Dependencies.firebase_crashlytics)

    kapt(AnnotationProcessing.hilt_dagger_compiler)
    kapt(AnnotationProcessing.hilt_viewmodel_compiler)
    kapt(AnnotationProcessing.permission_dispatcher_processor)
    kapt(AnnotationProcessing.glide_annotation)


    testImplementation(TestDependencies.junit4)

    androidTestImplementation(AndroidTestDependencies.espresso_core)
}