package com.gan.interbook.di

import com.gan.interbook.business.data.BooksRepository
import com.gan.interbook.business.data.abstraction.BooksDataSource
import com.gan.interbook.business.interactors.book.GetBooks
import com.gan.interbook.framework.network.api.BookApi
import com.gan.interbook.framework.network.implementation.NetworkBookDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookModule {
    @Provides
    @Singleton
    fun provideNetworkBookDataSource(
        bookApi: BookApi
    ): BooksDataSource = NetworkBookDataSource(bookApi)

    @Provides
    @Singleton
    fun provideBookRepository(
        booksDataSource: BooksDataSource
    ): BooksRepository =
        BooksRepository(booksDataSource)

    /**
     * Interactors
     */
    @Provides
    @Singleton
    fun provideGetBooks(
        booksRepository: BooksRepository
    ): GetBooks =
        GetBooks(booksRepository)
}