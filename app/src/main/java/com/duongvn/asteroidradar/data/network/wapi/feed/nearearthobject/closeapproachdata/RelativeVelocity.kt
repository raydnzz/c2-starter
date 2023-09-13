package com.duongvn.asteroidradar.data.network.wapi.feed.nearearthobject.closeapproachdata

import com.google.gson.annotations.SerializedName

data class RelativeVelocity(
    @SerializedName(value = "kilometers_per_hour")
    val kilometersPerHour: String,
    @SerializedName(value = "kilometers_per_second")
    val kilometersPerSecond: String,
    @SerializedName(value = "miles_per_hour")
    val milesPerHour: String
)