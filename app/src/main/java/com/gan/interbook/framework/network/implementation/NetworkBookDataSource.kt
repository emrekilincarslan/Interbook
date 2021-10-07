package com.gan.interbook.framework.network.implementation

import com.gan.interbook.business.data.abstraction.BooksDataSource
import com.gan.interbook.business.domain.book.BookListResponseModel
import com.gan.interbook.business.interactors.mapSuccess
import com.gan.interbook.framework.network.InterbookResult
import com.gan.interbook.framework.network.api.BookApi
import com.gan.interbook.framework.network.handleApiCall
import com.gan.interbook.framework.network.mapper.toBookListResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

class NetworkBookDataSource(
    private val bookApi: BookApi
) : BooksDataSource {
    override suspend fun fetchBookList(
        query: String,
        key: String,
        startIndex: Int
    ): Flow<InterbookResult<BookListResponseModel>> =
        flowOf(handleApiCall {
            bookApi.getBookList(
                query,
                key,
                startIndex
            )
        })
            .mapSuccess {
                it.toBookListResponseModel()
            }
}