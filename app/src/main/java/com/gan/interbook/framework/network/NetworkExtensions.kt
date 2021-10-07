package com.gan.interbook.framework.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gan.interbook.framework.network.NetworkErrors.NETWORK_ERROR_UNKNOWN
import com.gan.interbook.framework.network.model.common.ErrorObject
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

suspend fun <T> handleApiCall(
    apiCall: suspend () -> T
): InterbookResult<T> {
    return try {
        val value = apiCall.invoke()

        @Suppress("UNCHECKED_CAST")
        if ((value as? Response<Unit>) != null) {
            if (!value.isSuccessful) {
                throw HttpException(value)
            }
        }

        InterbookResult.Success(value)
    } catch (throwable: Throwable) {
        throwable.printStackTrace()

        when (throwable) {
            is HttpException -> {
                val code = throwable.code()
                val errorResponse = convertErrorBody(throwable)

                Timber.e(errorResponse)

                InterbookResult.GenericError(
                    code,
                    errorResponse
                )
            }
            else -> {
                Timber.e(throwable)
                InterbookResult.GenericError(
                    null,
                    NETWORK_ERROR_UNKNOWN
                )
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): String {
    return try {
        val gson = Gson()
        val type = object : TypeToken<ErrorObject>() {}.type
        val errorObject: ErrorObject? =
            gson.fromJson(throwable.response()?.errorBody()?.string(), type)
        return errorObject?.external?.message ?: GenericErrors.ERROR_UNKNOWN
    } catch (exception: Exception) {
        GenericErrors.ERROR_UNKNOWN
    }
}

object GenericErrors {
    const val ERROR_UNKNOWN = "Unknown error"
}