package com.gan.interbook.presentation.auth.viewmodel

import androidx.databinding.Bindable
import com.gan.interbook.BR
import com.gan.interbook.presentation.util.ObservableViewModel
import com.gan.interbook.presentation.util.SingleLiveEvent

class WarnUserViewModel : ObservableViewModel() {
    val userWarned = SingleLiveEvent<Unit>()
    val errorEvent = SingleLiveEvent<String>()

    @get:Bindable
    var boxClicked: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.submitEnabled)
        }

    @get:Bindable
    val submitEnabled: Boolean
        get() = boxClicked

    fun submitClicked() {
        userWarned.call()
    }
}