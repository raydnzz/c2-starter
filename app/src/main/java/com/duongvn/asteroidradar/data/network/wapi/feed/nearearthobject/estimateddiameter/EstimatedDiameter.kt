package com.duongvn.asteroidradar.data.network.wapi.feed.nearearthobject.estimateddiameter

import com.duongvn.asteroidradar.data.network.wapi.feed.nearearthobject.estimateddiameter.feet.Feet
import com.duongvn.asteroidradar.data.network.wapi.feed.nearearthobject.estimateddiameter.kilometer.Kilometers
import com.duongvn.asteroidradar.data.network.wapi.feed.nearearthobject.estimateddiameter.meter.Meters
import com.duongvn.asteroidradar.data.network.wapi.feed.nearearthobject.estimateddiameter.mile.Miles

data class EstimatedDiameter(
    val feet: Feet,
    val kilometers: Kilometers,
    val meters: Meters,
    val miles: Miles
)