package com.duongvn.asteroidradar.domain.home

import com.duongvn.asteroidradar.data.database.entity.Asteroid
import com.duongvn.asteroidradar.data.network.result.ResultAPI
import com.duongvn.asteroidradar.data.network.wapi.apod.Apod
import com.duongvn.asteroidradar.data.network.wapi.feed.ResponseFeed
import com.duongvn.asteroidradar.data.repositores.apod.ApodRepository
import com.duongvn.asteroidradar.data.repositores.asteroid.AsteroidRepository
import com.duongvn.asteroidradar.data.repositores.feed.FeedRepository
import com.duongvn.asteroidradar.ui.home.HomeViewModel.Companion.DATE_FORMAT
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AsteroidUseCaseImpl(
    private val asteroidRepository: AsteroidRepository,
    private val apodRepository: ApodRepository,
    private val feedRepository: FeedRepository
) : AsteroidUseCase {
    override fun executeGetAsteroidFromLocal(): Flow<List<Asteroid>> {
        return asteroidRepository.getAsteroids()
    }

    override suspend fun executeGetAsteroidFromServer(): List<Asteroid> {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        val date = getDate(dateFormat)
        val result = feedRepository.getResultFeed(
            startDate = date.first,
            endDate = date.second
        )
        return when (result) {
            is ResultAPI.SUCCESS<ResponseFeed> -> {
                result.data.nearEarthObjects.values.map {
                    it.map { nearEarthObject -> Asteroid.from(nearEarthObject = nearEarthObject) }
                }.flatten().also {
                    asteroidRepository.insertAll(*it.toTypedArray())
                }
            }

            is ResultAPI.ERROR -> emptyList()
        }
    }

    override suspend fun executeGetApod(): ResultAPI<Apod> {
        return apodRepository.getResultApod()
    }

    private fun getDate(dateFormat: SimpleDateFormat): Pair<String, String> {
        val now = Calendar.getInstance()
        val startDate = dateFormat.format(now.time)

        now.add(Calendar.DAY_OF_YEAR, 7)
        val endDate = dateFormat.format(now.time)
        return Pair(startDate, endDate)
    }
}