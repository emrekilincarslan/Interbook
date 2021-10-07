package com.gan.interbook.framework.network.model.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class External(
    @SerializedName("code")
    @Expose
    val code: Int,

    @SerializedName("message")
    @Expose
    val message: String?,

    @SerializedName("additional")
    @Expose
    val additional: String?,

    @SerializedName("exception")
    @Expose
    val exception: String?,

    @SerializedName("source")
    @Expose
    val source: String
)