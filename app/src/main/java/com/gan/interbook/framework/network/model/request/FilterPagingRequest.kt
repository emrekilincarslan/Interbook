package com.gan.interbook.framework.network.model.request

data class FilterPagingRequest(
    var titleFilter:String,
    var startIndex:Int
)
