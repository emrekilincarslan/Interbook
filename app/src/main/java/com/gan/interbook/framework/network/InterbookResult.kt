package com.gan.interbook.framework.network

sealed class InterbookResult<out T> {
    data class Success<out T>(val value: T) : InterbookResult<T>()

    data class GenericError(
        val code: Int? = null,
        val errorMessage: String? = null
    ) : InterbookResult<Nothing>()
}