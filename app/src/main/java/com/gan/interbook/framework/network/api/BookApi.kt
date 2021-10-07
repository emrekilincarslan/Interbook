package com.gan.interbook.framework.network.api

import com.gan.interbook.framework.network.model.response.book.BookListNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {

    @GET(VOLUMES)
    suspend fun getBookList(
        @Query("q") query: String,
        @Query("key") key: String,
        @Query("startIndex") startIndex: Int
    ): BookListNetworkEntity

    companion object {
        private const val VOLUMES = "volumes"
    }
}