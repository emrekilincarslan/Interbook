package com.gan.interbook.presentation.main.more

import androidx.hilt.lifecycle.ViewModelInject
import com.gan.interbook.BuildConfig
import com.gan.interbook.R
import com.gan.interbook.framework.preferences.SharedUserPreferences
import com.gan.interbook.framework.resources.abstraction.ResourceProvider
import com.gan.interbook.presentation.util.ObservableViewModel
import com.gan.interbook.presentation.util.SingleLiveEvent
import com.gan.interbook.util.Constants.SUPPORT_PHONE

class MoreViewModel @ViewModelInject constructor(
    resourceProvider: ResourceProvider,
    sharedUserPreferences: SharedUserPreferences
) : ObservableViewModel() {
    val privacyEvent = SingleLiveEvent<Unit>()
    val logoutEvent = SingleLiveEvent<Unit>()
    val callEvent = SingleLiveEvent<String>()

    val versionText: String =
        resourceProvider.getString(R.string.tv_version, BuildConfig.VERSION_NAME)

    val callButtonText: String =
        resourceProvider.getString(R.string.tv_support_phone, SUPPORT_PHONE)

    val screenTitleText: String = sharedUserPreferences.getUsername()

    fun privacyClicked() {
        privacyEvent.call()
    }

    fun logoutClicked() {
        logoutEvent.call()
    }

    fun callCLicked() {
        callEvent.value = SUPPORT_PHONE.filter { it.isDigit() }
    }
}