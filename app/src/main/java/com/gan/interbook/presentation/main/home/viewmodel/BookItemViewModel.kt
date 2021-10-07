package com.gan.interbook.presentation.main.home.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.gan.interbook.business.domain.book.BookItemModel
import com.gan.interbook.framework.resources.abstraction.ResourceProvider
import com.gan.interbook.presentation.common.ItemClickListener

class BookItemViewModel(
    resourceProvider: ResourceProvider,
    private val itemClickListener: ItemClickListener<BookItemModel>
) : BaseObservable() {
    var bookModel: BookItemModel? = null
        set(value) {
            field = value
            notifyChange()
        }

    fun itemClicked() {
        bookModel?.let {
            itemClickListener.onItemClicked(it)
        }
    }

    @get:Bindable
    val bookName: String?
        get() = bookModel?.volumeInfo?.title
}