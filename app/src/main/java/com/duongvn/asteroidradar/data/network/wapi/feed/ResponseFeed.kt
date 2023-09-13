package com.duongvn.asteroidradar.data.network.wapi.feed

import com.google.gson.annotations.SerializedName
import com.duongvn.asteroidradar.data.network.wapi.feed.link.Links
import com.duongvn.asteroidradar.data.network.wapi.feed.nearearthobject.NearEarthObject

data class ResponseFeed(
    @SerializedName(value = "element_count")
    val elementCount: Int,
    @SerializedName(value = "near_earth_objects")
    val nearEarthObjects : Map<String, List<NearEarthObject>>
)