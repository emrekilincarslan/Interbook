package com.gan.interbook.presentation.auth.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gan.interbook.R
import com.gan.interbook.databinding.FragmentIntroBinding
import com.gan.interbook.presentation.auth.Authenticator
import com.gan.interbook.presentation.auth.viewmodel.IntroViewModel
import com.gan.interbook.presentation.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroFragment : BaseFragment<FragmentIntroBinding>() {
    private val viewModel: IntroViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIntroBinding =
        FragmentIntroBinding.inflate(
            inflater,
            container,
            false
        )


    override fun onViewCreated(binding: FragmentIntroBinding) {
        binding.viewModel = viewModel

        viewModel.registerEvent.observe(viewLifecycleOwner) {
            (activity as? Authenticator)?.register()
        }

        viewModel.loginEvent.observe(viewLifecycleOwner) {
            (activity as? Authenticator)?.authorize()
        }

        viewModel.policyEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_global_policyFragment)
        }
    }
}