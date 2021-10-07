package com.gan.interbook.presentation.main.home

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.gan.interbook.R
import com.gan.interbook.databinding.FragmentBooksListBinding
import com.gan.interbook.presentation.main.home.viewmodel.SearchBookViewModel
import com.gan.interbook.presentation.main.home.viewmodel.SearchShareViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchBookFragment : BaseSearchFragment() {
    private val searchSharedViewModel: SearchShareViewModel by navGraphViewModels(R.id.home)
    override val viewModel: SearchBookViewModel by viewModels()

    private var navigationChangeListener: NavController.OnDestinationChangedListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigationChangeListener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                if (destination.id == R.id.search_screen) {
                    viewModel.updateBook(searchSharedViewModel.bookItemModel)
                }
            }
        navigationChangeListener?.let {
            findNavController().addOnDestinationChangedListener(it)
        }
    }

    override fun onViewCreated(binding: FragmentBooksListBinding) {
        super.onViewCreated(binding)

        viewModel.bookItemSelected.observe(viewLifecycleOwner, {
            searchSharedViewModel.bookItemModel = it
            Toast.makeText(activity, "yakaladi ${it.volumeInfo?.title}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_search_screen_to_book_screen)
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        navigationChangeListener?.let {
            findNavController().removeOnDestinationChangedListener(it)
        }
    }
}