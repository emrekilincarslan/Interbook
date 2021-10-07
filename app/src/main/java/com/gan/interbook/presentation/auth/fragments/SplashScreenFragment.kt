package com.gan.interbook.presentation.auth.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gan.interbook.databinding.FragmentSplashScreenBinding
import com.gan.interbook.presentation.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashScreenBinding =
        FragmentSplashScreenBinding.inflate(
            inflater,
            container,
            false
        )

    override fun onViewCreated(binding: FragmentSplashScreenBinding) {
    }
}