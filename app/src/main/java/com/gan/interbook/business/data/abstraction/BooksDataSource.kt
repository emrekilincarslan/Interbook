package com.gan.interbook.business.data.abstraction

import com.gan.interbook.business.domain.book.BookListResponseModel
import com.gan.interbook.framework.network.InterbookResult
import kotlinx.coroutines.flow.Flow

interface BooksDataSource {
    suspend fun fetchBookList(
        query: String,
        key: String,
        startIndex: Int
    ): Flow<InterbookResult<BookListResponseModel>>
}