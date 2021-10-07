package com.gan.interbook.framework.auth

import android.content.Intent
import android.net.Uri
import com.gan.interbook.business.domain.session.AuthProperties
import com.gan.interbook.framework.preferences.SharedUserPreferences
import net.openid.appauth.*
import timber.log.Timber

class AuthManager constructor(
    private val authProperties: AuthProperties,
    private val authorizationServiceConfiguration: AuthorizationServiceConfiguration,
    private val authorizationService: AuthorizationService,
    private val sharedUserPreferences: SharedUserPreferences
) {
    fun ensureAuthorised(onSuccess: () -> Unit, onFailure: () -> Unit) {
        val authState: AuthState =
            sharedUserPreferences.getAuthState() ?: return onFailure.invoke()

        return if (authState.isAuthorized) {
            Timber.d("authorized")
            if (authState.needsTokenRefresh) {
                Timber.d("token needs refresh")
                performTokenRequest(onSuccess, onFailure)
            } else {
                Timber.d("token doesn't need refresh")
                onSuccess.invoke()
            }
        } else {
            onFailure.invoke()
        }
    }

    private fun performTokenRequest(
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        sharedUserPreferences.getAuthState()?.let {
            authorizationService.performTokenRequest(
                it.createTokenRefreshRequest(),
                tokenResponseCallback(onSuccess, onFailure)
            )
        } ?: onFailure.invoke()
    }

    private fun performTokenRequest(
        response: AuthorizationResponse,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        authorizationService.performTokenRequest(
            response.createTokenExchangeRequest(),
            tokenResponseCallback(onSuccess, onFailure)
        )
    }

    private fun tokenResponseCallback(
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ): AuthorizationService.TokenResponseCallback =
        AuthorizationService.TokenResponseCallback { resp, ex ->
            if (resp != null) {
                updateAuthState(resp, ex)

                onSuccess.invoke()
            } else {
                nullifyAuthState()

                onFailure.invoke()
            }
        }

    fun getAuthorizationRequestIntent(register: Boolean): Intent {
        val authRequestBuilder: AuthorizationRequest.Builder =
            AuthorizationRequest.Builder(
                authorizationServiceConfiguration,
                authProperties.clientId,
                authProperties.responseType,
                Uri.parse(authProperties.redirectUri)
            )
                .setCodeVerifier(CodeVerifierUtil.generateRandomCodeVerifier())
                .setScope(authProperties.scope)
                .setPrompt(AuthorizationRequest.Prompt.LOGIN)

        if (register) {
            authRequestBuilder.setAdditionalParameters(
                mapOf(
                    Pair(
                        REGISTER_QUERY_PARAMETER,
                        authProperties.registerAction
                    )
                )
            )
        }

        return authorizationService.getAuthorizationRequestIntent(authRequestBuilder.build())
    }

    fun getEndSessionRequestIntent(): Intent {
        //todo
        val authState = sharedUserPreferences.getAuthState()
            ?: throw IllegalStateException("Auth session is null")

        val idToken = authState.refreshToken ?: throw IllegalStateException("Auth idToken is null")

        val endSessionRequest: EndSessionRequest = EndSessionRequest.Builder(
            authorizationServiceConfiguration,
            idToken,
            Uri.parse(authProperties.signOutRedirectUri)
        ).build()

        return authorizationService.getEndSessionRequestIntent(endSessionRequest)
    }

    fun handleAuthorizationResponse(
        response: AuthorizationResponse?,
        exception: AuthorizationException?,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        if (response != null) {
            Timber.d("AuthorizationResponse - success: ${response.authorizationCode}")
            setAuthState(response, exception)
            performTokenRequest(response, onSuccess, onFailure)
        } else {
            Timber.d("Authorization exception: $exception")
            nullifyAuthState()
        }
    }

    private fun setAuthState(
        authResponse: AuthorizationResponse,
        authException: AuthorizationException?
    ) {
        var authState = sharedUserPreferences.getAuthState()

        if (authState != null) {
            Timber.d("authstate dolu")
            authState.update(authResponse, authException)
        } else {
            Timber.d("authstate null geldi ")
            authState = AuthState(authResponse, authException)
        }
        Timber.d(" save authstate 145 scope =${authState?.scope} accessTokenExpirationTime =${authState?.accessTokenExpirationTime} idToken =${authState?.idToken} idToken =${authState?.refreshToken} ")
        sharedUserPreferences.saveAuthState(authState)
    }

    private fun updateAuthState(
        tokenResponse: TokenResponse,
        authException: AuthorizationException?
    ) {
        val authState = sharedUserPreferences.getAuthState()

        authState?.let {
            it.update(tokenResponse, authException)
            Timber.d(" save authstate 157 scope =${authState?.scope} accessTokenExpirationTime =${authState?.accessTokenExpirationTime} idToken =${authState?.idToken} refreshToken =${authState?.refreshToken} ")
            sharedUserPreferences.saveAuthState(it)
        }
    }

    fun nullifyAuthState() {
        Timber.d("NullifyState called.")
        sharedUserPreferences.clean()
    }

    companion object {
        private const val REGISTER_QUERY_PARAMETER = "redirectToAction"
    }
}