package com.gan.interbook.presentation.auth.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.gan.interbook.presentation.util.ObservableViewModel
import com.gan.interbook.presentation.util.SingleLiveEvent

class IntroViewModel @ViewModelInject constructor() : ObservableViewModel() {
    val registerEvent = SingleLiveEvent<Unit>()
    val loginEvent = SingleLiveEvent<Unit>()
    val policyEvent = SingleLiveEvent<Unit>()

    fun registerClicked() {
        registerEvent.call()
    }

    fun loginClicked() {
        loginEvent.call()
    }

    fun policyClicked() {
        policyEvent.call()
    }
}