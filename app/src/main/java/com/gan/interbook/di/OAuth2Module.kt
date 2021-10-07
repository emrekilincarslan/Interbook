package com.gan.interbook.di

import android.content.Context
import android.net.Uri
import com.gan.interbook.BuildConfig
import com.gan.interbook.business.domain.session.AuthProperties
import com.gan.interbook.framework.auth.AuthManager
import com.gan.interbook.framework.preferences.SharedUserPreferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OAuth2Module {
    @Provides
    @Singleton
    fun provideAuthProperties(): AuthProperties {
        return AuthProperties(
            authorizationEndPointUri = BuildConfig.AUTHORIZATION_END_POINT_URI,
            apiKey = BuildConfig.API_KEY,
            clientId = BuildConfig.CLIENT_ID,
            endSession = BuildConfig.END_SESSION,
            redirectUri = BuildConfig.REDIRECT_URI,
            responseType = BuildConfig.RESPONSE_TYPE,
            scope = BuildConfig.SCOPE,
            tokenEndPointUri = BuildConfig.TOKEN_END_POINT_URI,
            signOutRedirectUri = BuildConfig.SIGN_OUT_URI,
            registerAction = BuildConfig.REGISTER_PARAM
        )
    }

    @Provides
    @Singleton
    fun provideAuthorizationServiceConfiguration(authProperties: AuthProperties): AuthorizationServiceConfiguration {
        return AuthorizationServiceConfiguration(
            Uri.parse(authProperties.authorizationEndPointUri),
            Uri.parse(authProperties.tokenEndPointUri),
            Uri.parse(authProperties.endSession),
            // passing null won't be necessary in the next version of the auth library.
            // Three parameter constructor will be available.
            null
        )
    }

    @Provides
    @Singleton
    fun provideAuthorizationService(
        @ApplicationContext context: Context
    ): AuthorizationService {
        return AuthorizationService(context)
    }

    @Provides
    @Singleton
    fun provideGoogleSignInOptions(authProperties: AuthProperties): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(authProperties.clientId)
            .requestEmail().build()
    }

    @Provides
    @Singleton
    fun provideGoogleSignInClient(
        @ApplicationContext context: Context,
        gso: GoogleSignInOptions
    ): GoogleSignInClient {
        return GoogleSignIn.getClient(context,gso)
    }

    @Provides
    @Singleton
    fun provideAuthManager(
        authProperties: AuthProperties,
        authorizationServiceConfiguration: AuthorizationServiceConfiguration,
        authorizationService: AuthorizationService,
        sharedUserPreferences: SharedUserPreferences
    ): AuthManager {
        return AuthManager(
            authProperties,
            authorizationServiceConfiguration,
            authorizationService,
            sharedUserPreferences
        )
    }
}