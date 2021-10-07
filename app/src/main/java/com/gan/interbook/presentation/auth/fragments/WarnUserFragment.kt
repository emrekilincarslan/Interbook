package com.gan.interbook.presentation.auth.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.gan.interbook.databinding.FragmentWarnUserBinding
import com.gan.interbook.presentation.auth.FreeCountryChecker
import com.gan.interbook.presentation.auth.viewmodel.WarnUserViewModel
import com.gan.interbook.presentation.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WarnUserFragment : BaseFragment<FragmentWarnUserBinding>() {
    private val viewModel: WarnUserViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWarnUserBinding =
        FragmentWarnUserBinding.inflate(
            inflater,
            container,
            false
        )

    override fun onViewCreated(binding: FragmentWarnUserBinding) {
        binding.viewModel = viewModel


        viewModel.userWarned.observe(viewLifecycleOwner, {
            (activity as? FreeCountryChecker)?.proceedWithWarning()
        })

        viewModel.errorEvent.observe(viewLifecycleOwner, {
            displayErrorDialog(it)
        })
    }
}
