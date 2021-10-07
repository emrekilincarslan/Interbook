package com.gan.interbook.presentation.main.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gan.interbook.R
import com.gan.interbook.databinding.FragmentMoreBinding
import com.gan.interbook.presentation.common.PhoneDialer
import com.gan.interbook.presentation.common.SessionFinisher
import com.gan.interbook.presentation.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : BaseFragment<FragmentMoreBinding>() {
    private val viewModel: MoreViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMoreBinding =
        FragmentMoreBinding.inflate(
            inflater,
            container,
            false
        )

    override fun onViewCreated(binding: FragmentMoreBinding) {
        binding.viewModel = viewModel

        viewModel.privacyEvent.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.action_more_screen_to_policy_screen)
        })

        viewModel.logoutEvent.observe(viewLifecycleOwner, {
            (activity as? SessionFinisher)?.endSession()
        })

        viewModel.callEvent.observe(viewLifecycleOwner, {
            (activity as? PhoneDialer)?.dial(it)
        })
    }
}