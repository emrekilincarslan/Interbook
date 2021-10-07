package com.gan.interbook.presentation.common.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gan.interbook.databinding.FragmentPolicyBinding
import com.gan.interbook.framework.network.LoadingObserverWebViewClient
import com.gan.interbook.presentation.common.base.BaseFragment
import com.gan.interbook.util.Constants.POLICY_URL
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PolicyFragment : BaseFragment<FragmentPolicyBinding>() {
    @Inject
    lateinit var loadingObserverWebViewClient: LoadingObserverWebViewClient

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPolicyBinding = FragmentPolicyBinding.inflate(
        inflater,
        container,
        false
    )


    override fun onViewCreated(binding: FragmentPolicyBinding) {
        loadPolicy(binding)
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun loadPolicy(binding: FragmentPolicyBinding) {
        binding.webView.apply {
            loadUrl(POLICY_URL)
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webViewClient = loadingObserverWebViewClient
        }
    }
}
