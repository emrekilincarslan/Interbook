package com.gan.interbook.presentation.main.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.gan.interbook.R
import com.gan.interbook.business.domain.book.BookItemModel
import com.gan.interbook.business.domain.book.BookListResponseModel
import com.gan.interbook.business.interactors.book.GetBooks
import com.gan.interbook.framework.network.InterbookResult
import com.gan.interbook.framework.preferences.SharedUserPreferences
import com.gan.interbook.framework.resources.abstraction.ResourceProvider
import com.gan.interbook.presentation.util.SingleLiveEvent
import kotlinx.coroutines.flow.Flow

class SearchBookViewModel @ViewModelInject constructor(
    resourceProvider: ResourceProvider,
    private val userPreferences: SharedUserPreferences,
    private val getBooks: GetBooks

) : BaseSearchViewModel(resourceProvider, getBooks) {
    val bookItemSelected = SingleLiveEvent<BookItemModel>()

    override val noBooksText: String
        get() = resourceProvider.getString(R.string.books_empty_message)

    override suspend fun getBooksCall(getBooks: GetBooks): Flow<InterbookResult<BookListResponseModel>> =
        getBooks.invoke(if (query.isNullOrEmpty()) "android" else query, currentPage)

    override fun onItemClicked(item: BookItemModel) {
        bookItemSelected.value = item
    }

    fun updateBook(bookItemModel: BookItemModel?) {
        if (bookItemModel?.saleInfo == null) return
        val indexOfTrip = books.indexOfFirst { it.id == bookItemModel.id }
        if (indexOfTrip < 0) return

        books.removeAt(indexOfTrip)
        books.add(indexOfTrip, bookItemModel)
    }
}

