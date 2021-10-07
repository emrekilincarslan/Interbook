package com.gan.interbook.presentation.main.home.details.viewmodel

import androidx.databinding.Bindable
import androidx.hilt.lifecycle.ViewModelInject
import com.gan.interbook.BR
import com.gan.interbook.business.domain.book.BookItemModel
import com.gan.interbook.framework.resources.abstraction.ResourceProvider
import com.gan.interbook.presentation.util.ObservableViewModel

class BookViewModel @ViewModelInject constructor(
    private val resourceProvider: ResourceProvider
) : ObservableViewModel() {
    var bookItemModel: BookItemModel? = null
        set(value) {
            field = value
            bookTitle = "Title : " + field?.volumeInfo?.title ?: "no title"
            bookHeaderUrl = field?.volumeInfo?.imageLinks?.thumbnail ?: ""
            released = "Published : " + field?.volumeInfo?.publishedDate ?: ""
            authors = "by " + field?.volumeInfo?.authors.toString()
            publisher = "publisher " + field?.volumeInfo?.publisher ?: ""
            language = "language " + field?.volumeInfo?.language ?: ""
            previewLink = "preview Link " + field?.volumeInfo?.previewLink ?: ""
            notifyPropertyChanged(BR._all)
            notifyChange()
        }

    @get:Bindable
    var bookTitle: String = ""

    @get:Bindable
    var released: String = ""

    @get:Bindable
    var authors: String = ""

    @get:Bindable
    var publisher: String = ""

    @get:Bindable
    var language: String = ""

    @get:Bindable
    var previewLink: String = ""


    @get:Bindable
    var bookHeaderUrl: String = ""
        set(value) {
            field = value.replace("http", "https")
            notifyPropertyChanged(BR.bookHeaderUrl)
        }


}