package com.gan.interbook.business.interactors

import com.gan.interbook.framework.network.InterbookResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform

inline fun <T> Flow<InterbookResult<T>>.doOnSuccess(crossinline action: (T) -> Unit): Flow<InterbookResult<T>> =
    flow {
        collect {
            if (it is InterbookResult.Success<T>) {
                action.invoke(it.value)
            }

            emit(it)
        }
    }

inline fun <T, R> Flow<InterbookResult<T>>.mapSuccess(crossinline convert: (T) -> R): Flow<InterbookResult<R>> =
    transform {
        if (it is InterbookResult.Success) {
            emit(InterbookResult.Success(convert(it.value)))
        } else {
            emit(it as InterbookResult<R>)
        }
    }

suspend inline fun <T> Flow<InterbookResult<T>>.collect(
    crossinline onSuccess: suspend (T) -> Unit,
    crossinline onFailure: suspend (InterbookResult.GenericError) -> Unit
) {
    collect {
        if (it is InterbookResult.Success) {
            onSuccess(it.value)
        } else if (it is InterbookResult.GenericError) {
            onFailure(it)
        }
    }
}