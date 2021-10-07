package com.gan.interbook.framework.auth

import com.gan.interbook.framework.preferences.SharedUserPreferences
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TokenRefreshAuthenticator(
    private val authManager: AuthManager,
    private val userPreferences: SharedUserPreferences
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) > TOKEN_REFRESH_RETRIES) return null
        Timber.d("refreshing token")

        synchronized(this) {
            return runBlocking {
                val shouldRetryRequest = suspendCoroutine<Boolean> {
                    authManager.ensureAuthorised(
                        {
                            it.resume(true)
                        },
                        {
                            it.resume(false)
                        }
                    )
                }

                return@runBlocking if (shouldRetryRequest) {
                    requestWithNewToken(
                        response.request
                    )
                } else {
                    null
                }
            }
        }
    }

    private fun responseCount(originalResponse: Response): Int {
        var response: Response? = originalResponse
        var result = 0

        while (response != null) {
            response = response.priorResponse
            result++
        }

        return result
    }

    private fun requestWithNewToken(
        request: Request
    ): Request {
        return request.newBuilder()
            .setAuthorizationHeader(userPreferences)
            .build()
    }

    companion object {
        private const val TOKEN_REFRESH_RETRIES = 3

        const val AUTHORIZATION_HEADER = "Authorization"
        const val AUTHORIZATION_TOKEN_PREFIX = "Bearer "
    }
}

fun Request.Builder.setAuthorizationHeader(userPreferences: SharedUserPreferences): Request.Builder {
    val accessToken = userPreferences.getAuthState()?.accessToken
    Timber.d("$accessToken")

    return this
        .apply {
            accessToken?.let { accessToken ->
                this.header(
                    TokenRefreshAuthenticator.AUTHORIZATION_HEADER,
                    TokenRefreshAuthenticator.AUTHORIZATION_TOKEN_PREFIX + accessToken
                )
            }
        }
}
