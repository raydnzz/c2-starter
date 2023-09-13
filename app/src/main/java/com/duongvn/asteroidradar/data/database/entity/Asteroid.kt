package com.duongvn.asteroidradar.data.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.duongvn.asteroidradar.data.network.wapi.feed.nearearthobject.NearEarthObject
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "asteroid")
data class Asteroid(
    @PrimaryKey
    val id: Long,
    val name: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean,
) : Parcelable {
    companion object {
        fun from(nearEarthObject: NearEarthObject): Asteroid {
            return nearEarthObject.run {
                closeApproachData.first().let {
                    Asteroid(
                        id = id.toLong(),
                        name = name,
                        closeApproachDate = it.closeApproachDate,
                        absoluteMagnitude = absoluteMagnitudeH,
                        estimatedDiameter = estimatedDiameter.kilometers.estimatedDiameterMax,
                        relativeVelocity = it.relativeVelocity.kilometersPerSecond.toDouble(),
                        distanceFromEarth = it.missDistance.astronomical.toDouble(),
                        isPotentiallyHazardous = isPotentiallyHazardousAsteroid
                    )
                }
            }
        }
    }
}
