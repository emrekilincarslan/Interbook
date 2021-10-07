package com.gan.interbook.presentation.main.home.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.gan.interbook.R
import com.gan.interbook.databinding.FragmentBookBinding
import com.gan.interbook.presentation.common.base.BaseFragment
import com.gan.interbook.presentation.main.home.details.viewmodel.BookViewModel
import com.gan.interbook.presentation.main.home.viewmodel.SearchShareViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookFragment : BaseFragment<FragmentBookBinding>() {
    private val searchShareViewModel: SearchShareViewModel by navGraphViewModels(R.id.home)
    private val viewModel: BookViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBookBinding =
        FragmentBookBinding.inflate(
            inflater,
            container,
            false
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.bookItemModel = searchShareViewModel.bookItemModel
    }

    override fun onViewCreated(binding: FragmentBookBinding) {
        viewModel.bookItemModel = searchShareViewModel.bookItemModel
        binding.viewModel = viewModel

    }
}