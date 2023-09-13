package com.duongvn.asteroidradar.data.network.wapi.feed.nearearthobject

import com.google.gson.annotations.SerializedName
import com.duongvn.asteroidradar.data.network.wapi.feed.nearearthobject.closeapproachdata.CloseApproachData
import com.duongvn.asteroidradar.data.network.wapi.feed.nearearthobject.estimateddiameter.EstimatedDiameter
import com.duongvn.asteroidradar.data.network.wapi.feed.nearearthobject.link.Links

data class NearEarthObject(
    @SerializedName(value = "absolute_magnitude_h")
    val absoluteMagnitudeH: Double,
    @SerializedName(value = "close_approach_data")
    val closeApproachData: List<CloseApproachData>,
    @SerializedName(value = "estimated_diameter")
    val estimatedDiameter: EstimatedDiameter,
    val id: String,
    @SerializedName(value = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean,
    @SerializedName(value = "is_sentry_object")
    val isSentryObject: Boolean,
    val links: Links,
    val name: String,
    @SerializedName(value = "nasa_jpl_url")
    val nasaJplUrl: String,
    @SerializedName(value = "neo_reference_id")
    val neoReferenceId: String
)