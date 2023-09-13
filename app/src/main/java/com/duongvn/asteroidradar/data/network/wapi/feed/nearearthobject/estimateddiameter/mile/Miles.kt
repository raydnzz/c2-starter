package com.duongvn.asteroidradar.data.network.wapi.feed.nearearthobject.estimateddiameter.mile

import com.google.gson.annotations.SerializedName

data class Miles(
    @SerializedName(value = "estimated_diameter_max")
    val estimatedDiameterMax: Double,
    @SerializedName(value = "estimated_diameter_min")
    val estimatedDiameterMin: Double
)