package com.gan.interbook.presentation.main.home.viewmodel

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.SearchView
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gan.interbook.R
import com.gan.interbook.business.domain.book.BookItemModel
import com.gan.interbook.business.domain.book.BookListResponseModel
import com.gan.interbook.business.interactors.book.GetBooks
import com.gan.interbook.business.interactors.collect
import com.gan.interbook.framework.network.InterbookResult
import com.gan.interbook.framework.network.NetworkConstants
import com.gan.interbook.framework.resources.abstraction.ResourceProvider
import com.gan.interbook.presentation.common.ItemClickListener
import com.gan.interbook.presentation.util.ObservableViewModel
import com.gan.interbook.presentation.util.SingleLiveEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseSearchViewModel(
    protected val resourceProvider: ResourceProvider,
    private val getBooks: GetBooks
) : ObservableViewModel(), ItemClickListener<BookItemModel> {
    val errorEvent = SingleLiveEvent<String>()

    protected val books: MutableList<BookItemModel> = mutableListOf()
    private val currentBooks = MutableLiveData<List<BookItemModel>>(books)

    fun books(): LiveData<List<BookItemModel>> = currentBooks

    protected var currentPage: Int = NetworkConstants.PAGINATION_INITIAL_INDEX
        protected set(value) {
            field = value
            queryExhausted = false
        }
    private var queryExhausted: Boolean = false

    init {
        fetchBooks()
    }

    @get:Bindable
    abstract val noBooksText: String

    @get:Bindable
    val noBooksVisibility: Boolean
        get() = books.isEmpty()

    @get:Bindable
    val headerImage: Drawable?
        get() = if (books.isEmpty()) {
            resourceProvider.getDrawable(R.drawable.header_tall)
        } else {
            resourceProvider.getDrawable(R.drawable.header_short)
        }

    var query: String = "android"

    var onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                this@BaseSearchViewModel.query = query
                refresh()
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    }

    fun refresh() {
        books.clear()
        currentPage = NetworkConstants.PAGINATION_INITIAL_INDEX
        fetchBooks()
    }

    fun loadNextPage() {
        if (queryExhausted) return

        currentPage += 10
        fetchBooks()
    }

    private fun fetchBooks() {
        viewModelScope.launch {
            getBooksCall(getBooks)
                .collect(
                    {
                        queryExhausted = it.items?.isEmpty() ?: true

                        it.items?.let { newbooks -> books += newbooks }
                        currentBooks.value = books
                        notifyChange()
                    },
                    {
                        errorEvent.value = it.errorMessage
                        notifyChange()
                    }
                )
        }
    }

    abstract suspend fun getBooksCall(getBooks: GetBooks): Flow<InterbookResult<BookListResponseModel>>
}