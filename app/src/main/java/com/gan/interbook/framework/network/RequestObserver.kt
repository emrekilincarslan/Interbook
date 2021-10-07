package com.gan.interbook.framework.network

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import java.util.concurrent.atomic.AtomicInteger

class RequestObserver {
    private val mutableStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val activeRequests: AtomicInteger = AtomicInteger()

    val observeRequests: StateFlow<Boolean> =
        mutableStateFlow

    fun markRequestStarted() {
        Timber.d("requestStarted")
        activeRequests.incrementAndGet()
        notifyObservers()
    }

    fun markRequestFinished() {
        Timber.d("requestFinished")
        activeRequests.decrementAndGet()
        notifyObservers()
    }

    private fun notifyObservers() {
        mutableStateFlow.value = activeRequests.get() != 0
    }
}