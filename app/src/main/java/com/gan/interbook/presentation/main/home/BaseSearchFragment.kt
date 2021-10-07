package com.gan.interbook.presentation.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gan.interbook.databinding.FragmentBooksListBinding
import com.gan.interbook.framework.resources.abstraction.ResourceProvider
import com.gan.interbook.presentation.common.RecyclerPaginationScrollListener
import com.gan.interbook.presentation.common.base.BaseFragment
import com.gan.interbook.presentation.main.home.viewmodel.BaseSearchViewModel
import javax.inject.Inject

abstract class BaseSearchFragment : BaseFragment<FragmentBooksListBinding>() {
    abstract val viewModel: BaseSearchViewModel

    @Inject
    lateinit var resourceProvider: ResourceProvider

    private var recyclerAdapter: BooksAdapter? = null

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBooksListBinding =
        FragmentBooksListBinding.inflate(
            inflater,
            container,
            false
        )

    override fun onViewCreated(binding: FragmentBooksListBinding) {
        binding.viewModel = viewModel

        recyclerAdapter =
            BooksAdapter(resourceProvider, viewModel)

        binding.srlBooksList.setOnRefreshListener {
            viewModel.refresh()
        }

        binding.rvBooksList.addOnScrollListener(
            RecyclerPaginationScrollListener {
                viewModel.loadNextPage()
            }
        )

        viewModel.books().observe(viewLifecycleOwner, {
            binding.srlBooksList.isRefreshing = false

            recyclerAdapter?.setData(it)
        })

        viewModel.errorEvent.observe(viewLifecycleOwner, {
            displayErrorDialog(it)
        })

        binding.rvBooksList.adapter = recyclerAdapter
    }
}