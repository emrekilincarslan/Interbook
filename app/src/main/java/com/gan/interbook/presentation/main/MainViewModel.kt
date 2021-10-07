package com.gan.interbook.presentation.main

import androidx.databinding.Bindable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.viewModelScope
import com.gan.interbook.BR
import com.gan.interbook.framework.network.RequestObserver
import com.gan.interbook.presentation.util.ObservableViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val requestObserver: RequestObserver
) : ObservableViewModel(), LifecycleObserver {
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
}