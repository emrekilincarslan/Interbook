package com.gan.interbook.presentation.main.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.gan.interbook.business.domain.book.BookItemModel

class SearchShareViewModel @ViewModelInject constructor() : ViewModel() {
    var bookItemModel: BookItemModel? = null
}