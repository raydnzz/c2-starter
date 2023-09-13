package com.duongvn.asteroidradar.data.network.wapi.feed.nearearthobject.closeapproachdata

import com.google.gson.annotations.SerializedName

data class CloseApproachData(
    @SerializedName(value = "close_approach_date")
    val closeApproachDate: String,
    @SerializedName(value = "close_approach_date_full")
    val closeApproachDateFull: String,
    @SerializedName(value = "epoch_date_close_approach")
    val epochDateCloseApproach: Long,
    @SerializedName(value = "miss_distance")
    val missDistance: MissDistance,
    @SerializedName(value = "orbiting_body")
    val orbitingBody: String,
    @SerializedName(value = "relative_velocity")
    val relativeVelocity: RelativeVelocity
)