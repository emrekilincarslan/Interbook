package com.gan.interbook.framework.network.mapper

import com.gan.interbook.business.domain.book.*
import com.gan.interbook.framework.network.model.response.book.*

fun BookListNetworkEntity.toBookListResponseModel(): BookListResponseModel =
    BookListResponseModel(
        totalItems = totalItems,
        items = bookList?.mapBookList()
    )

private fun List<BookItemEntity>.mapBookList(): List<BookItemModel> =
    map {
        BookItemModel(
            id = it.id,
            etag = it.eTag,
            volumeInfo = it.volumeInfo?.mapBookVolumeInfoModel(),
            accessInfo = it.accessInfoEntity.mapAccessInfoModel(),
            saleInfo = it.saleInfo?.mapSalesInfoModel()
        )
    }

private fun SaleInfoEntity.mapSalesInfoModel(): SaleInfoModel =
    SaleInfoModel(
        buyLink = buyLink,
        retailPriceEntity = retailPriceEntity?.mapPriceModel(),
        country = country,
        saleability = saleability,
        isEbook = isEbook
    )

private fun PriceEntity.mapPriceModel(): PriceModel =
    PriceModel(
        amount = amount,
        currencyCode = currencyCode
    )

private fun AccessInfoEntity.mapAccessInfoModel(): AccessInfoModel =
    AccessInfoModel(
        webReaderLink = webReaderLink
    )

private fun BookVolumeInfoEntity.mapBookVolumeInfoModel(): BookVolumeInfoModel =
    BookVolumeInfoModel(
        title = title,
        subtitle = subtitle,
        description = description,
        publisher = publisher,
        language = language,
        authors = authors,
        publishedDate = publishedDate,
        pageCount = pageCount,
        averageRating = averageRating,
        ratingsCount = ratingsCount,
        imageLinks = imageLinks?.mapImageLinksModel(),
        previewLink = previewLink
    )

private fun ImageLinksEntity.mapImageLinksModel(): ImageLinksModel =
    ImageLinksModel(
        thumbnail = thumbnail,
        smallThumbnail=smallThumbnail
    )


