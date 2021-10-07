package com.gan.interbook.presentation.auth

import androidx.databinding.Bindable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.viewModelScope
import com.gan.interbook.BR
import com.gan.interbook.business.interactors.book.GetBooks
import com.gan.interbook.business.interactors.collect
import com.gan.interbook.framework.network.InterbookResult
import com.gan.interbook.framework.network.RequestObserver
import com.gan.interbook.presentation.util.ObservableViewModel
import com.gan.interbook.presentation.util.SingleLiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthViewModel @ViewModelInject constructor(
    private val getBooks: GetBooks,
    private val requestObserver: RequestObserver
) : ObservableViewModel(), LifecycleObserver {
    val mustWarnUser = SingleLiveEvent<Unit>()
    val userHasRights = SingleLiveEvent<Unit>()
    val errorEvent = SingleLiveEvent<String>()

    @get:Bindable
    var progressDialogVisibility: Boolean = false
        private set(value) {
            field = value
            notifyPropertyChanged(BR.progressDialogVisibility)
        }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        viewModelScope.launch {
            requestObserver.observeRequests
                .collect {
                    progressDialogVisibility = it
                }
        }
    }

    fun getBooks() {
        viewModelScope.launch {
            getBooks.invoke("android")
                .collect(
                    onSuccess = {
                        if (!it.items.isNullOrEmpty() && it.items!![0].saleInfo?.country.equals(
                                "BG",
                                true
                            )
                        ) {
                            mustWarnUser.call()
                        } else {
                            userHasRights.call()
                        }

                    },
                    onFailure = {
                        handleError(it)
                    }
                )
        }
    }

    private fun handleError(error: InterbookResult.GenericError) {
        errorEvent.value = error.errorMessage
    }
}