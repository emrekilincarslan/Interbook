package com.gan.interbook.framework.network.model.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StringValue(
    @SerializedName("value")
    @Expose
    val value: String?
)