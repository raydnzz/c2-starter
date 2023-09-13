package com.duongvn.asteroidradar.data.network.wapi.feed.nearearthobject.estimateddiameter.kilometer

import com.google.gson.annotations.SerializedName

data class Kilometers(
    @SerializedName(value = "estimated_diameter_max")
    val estimatedDiameterMax: Double,
    @SerializedName(value = "estimated_diameter_min")
    val estimatedDiameterMin: Double
)