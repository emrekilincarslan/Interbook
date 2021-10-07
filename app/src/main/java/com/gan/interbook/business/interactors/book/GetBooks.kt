package com.gan.interbook.business.interactors.book

import com.gan.interbook.BuildConfig
import com.gan.interbook.business.data.BooksRepository
import com.gan.interbook.business.domain.book.BookListResponseModel
import com.gan.interbook.framework.network.InterbookResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetBooks(
    private val booksRepository: BooksRepository
) {
    suspend fun invoke(
        query: String,
        startIndex: Int=0
    ): Flow<InterbookResult<BookListResponseModel>> {
        return booksRepository.getBookList(
            query,
            BuildConfig.API_KEY,
            startIndex
        )
            .flowOn(Dispatchers.IO)
    }
}