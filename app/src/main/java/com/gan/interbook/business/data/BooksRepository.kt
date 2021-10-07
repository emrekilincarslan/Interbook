package com.gan.interbook.business.data

import com.gan.interbook.business.data.abstraction.BooksDataSource
import com.gan.interbook.business.domain.book.BookListResponseModel
import com.gan.interbook.framework.network.InterbookResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class BooksRepository(private val booksDataSource: BooksDataSource) {
    suspend fun getBookList(
        query: String,
        key: String,
        startIndex: Int
    ): Flow<InterbookResult<BookListResponseModel>> =
        booksDataSource.fetchBookList(
            query,
            key,
            startIndex
        )
            .flowOn(Dispatchers.IO)
}