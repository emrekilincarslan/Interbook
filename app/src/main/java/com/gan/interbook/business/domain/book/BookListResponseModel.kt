package com.gan.interbook.business.domain.book

import java.math.BigDecimal

data class BookListResponseModel(
    var totalItems: Int,
    var items: List<BookItemModel>?
)

data class BookItemModel(
    var id: String,
    var etag: String,
    var volumeInfo: BookVolumeInfoModel?,
    var accessInfo: AccessInfoModel,
    var saleInfo: SaleInfoModel?
)

data class BookVolumeInfoModel(
    var title: String?,
    var subtitle: String?,
    var description: String?,
    var publisher: String?,
    var language: String?,
    var authors: List<String>?,
    var publishedDate: String?,
    var pageCount: Int?,
    var averageRating: Double?,
    var ratingsCount: Int?,
    var imageLinks: ImageLinksModel?,
    var previewLink: String?
)

data class AccessInfoModel(
    var webReaderLink: String
)

data class SaleInfoModel(
    var buyLink: String?,
    var retailPriceEntity: PriceModel?,
    var country: String?,
    var saleability: String?,
    var isEbook: Boolean?
    )

data class PriceModel(
    var amount: BigDecimal?,
    var currencyCode: String?
)

data class ImageLinksModel(
    var thumbnail: String?,
    var smallThumbnail: String?
)


