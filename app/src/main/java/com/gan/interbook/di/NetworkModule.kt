package com.gan.interbook.di

import com.gan.interbook.BuildConfig
import com.gan.interbook.framework.auth.AuthManager
import com.gan.interbook.framework.auth.TokenRefreshAuthenticator
import com.gan.interbook.framework.auth.setAuthorizationHeader
import com.gan.interbook.framework.network.LoadingObserverWebViewClient
import com.gan.interbook.framework.network.NetworkConstants
import com.gan.interbook.framework.network.RequestObserver
import com.gan.interbook.framework.network.api.BookApi
import com.gan.interbook.framework.preferences.SharedUserPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
    }

    @Provides
    @Singleton
    fun provideRequestObserver(): RequestObserver {
        return RequestObserver()
    }

    @Provides
    @Singleton
    fun provideAuthenticator(
        authManager: AuthManager,
        userPreferences: SharedUserPreferences
    ): Authenticator {
        return TokenRefreshAuthenticator(authManager, userPreferences)
    }

    @Provides
    @Singleton
    fun provideOkHttpBuilder(
        sharedUserPreferences: SharedUserPreferences,
        requestObserver: RequestObserver,
        authenticator: Authenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(NetworkConstants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NetworkConstants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetworkConstants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .authenticator(authenticator)
            .addInterceptor { chain ->
                val request = chain.request()
                requestObserver.markRequestStarted()

                try {
                    chain.proceed(request)
                } finally {
                    requestObserver.markRequestFinished()
                }
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
                })
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("X-ClientId", BuildConfig.CLIENT_ID)
                    .setAuthorizationHeader(sharedUserPreferences)
                    .build()

                chain.proceed(request)
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        gsonBuilder: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideBookApi(retrofit: Retrofit): BookApi {
        return retrofit.create(BookApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoadingObserverWebViewClient(requestObserver: RequestObserver): LoadingObserverWebViewClient =
        LoadingObserverWebViewClient(requestObserver)
}