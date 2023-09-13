package com.duongvn.asteroidradar.data.network.wapi.apod

import com.google.gson.annotations.SerializedName

data class Apod(
    val date: String = "",
    val explanation: String = "",
    @SerializedName(value = "media_type")
    val mediaType: String = "",
    val title: String = "",
    val url: String = ""
)