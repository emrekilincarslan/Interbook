package com.gan.interbook.framework.network.model.response.book

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class BookListNetworkEntity(

    @SerializedName("totalItems")
    @Expose
    val totalItems: Int,

    @SerializedName("items")
    @Expose
    var bookList: List<BookItemEntity>?
)

data class BookItemEntity(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("etag")
    @Expose
    var eTag: String,

    @SerializedName("volumeInfo")
    @Expose
    var volumeInfo: BookVolumeInfoEntity?,

    @SerializedName("accessInfo")
    @Expose
    var accessInfoEntity: AccessInfoEntity,

    @SerializedName("saleInfo")
    @Expose
    var saleInfo: SaleInfoEntity?
)

data class BookVolumeInfoEntity(
    @SerializedName("title")
    @Expose
    var title: String?,

    @SerializedName("subtitle")
    @Expose
    var subtitle: String?,

    @SerializedName("description")
    @Expose
    var description: String?,

    @SerializedName("publisher")
    @Expose
    var publisher: String?,

    @SerializedName("language")
    @Expose
    var language: String?,

    @SerializedName("authors")
    @Expose
    var authors: List<String>?,

    @SerializedName("publishedDate")
    @Expose
    var publishedDate: String?,

    @SerializedName("pageCount")
    @Expose
    var pageCount: Int?,

    @SerializedName("averageRating")
    @Expose
    var averageRating: Double?,

    @SerializedName("ratingsCount")
    @Expose
    var ratingsCount: Int?,

    @SerializedName("imageLinks")
    @Expose
    var imageLinks: ImageLinksEntity?,

    @SerializedName("previewLink")
    @Expose
    var previewLink: String?

)

data class ImageLinksEntity(
    @SerializedName("thumbnail")
    @Expose
    var thumbnail: String?,

    @SerializedName("smallThumbnail")
    @Expose
    var smallThumbnail: String?,


)

data class AccessInfoEntity(

    @SerializedName("webReaderLink")
    @Expose
    var webReaderLink: String
)

data class SaleInfoEntity(

    @SerializedName("buyLink")
    @Expose
    var buyLink: String?,

    @SerializedName("retailPrice")
    @Expose
    var retailPriceEntity: PriceEntity?,

    @SerializedName("country")
    @Expose
    var country: String?,

    @SerializedName("saleability")
    @Expose
    var saleability: String?,

    @SerializedName("isEbook")
    @Expose
    var isEbook: Boolean?
)

data class PriceEntity(

    @SerializedName("amount")
    @Expose
    var amount: BigDecimal?,

    @SerializedName("currencyCode")
    @Expose
    var currencyCode: String?
)





