package com.duongvn.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.duongvn.asteroidradar.data.database.AppDatabase
import com.duongvn.asteroidradar.data.database.entity.Asteroid
import com.duongvn.asteroidradar.data.datasource.asteroid.AsteroidDataSourceImpl
import com.duongvn.asteroidradar.data.datasource.feed.FeedDataSourceImpl
import com.duongvn.asteroidradar.data.network.APIConfig
import com.duongvn.asteroidradar.data.network.result.ResultAPI
import com.duongvn.asteroidradar.data.network.wapi.feed.ResponseFeed
import com.duongvn.asteroidradar.data.repositores.asteroid.AsteroidRepositoryImpl
import com.duongvn.asteroidradar.data.repositores.feed.FeedRepositoryImpl
import com.duongvn.asteroidradar.ui.home.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val feedRepository = FeedRepositoryImpl(
            feedDataSource = FeedDataSourceImpl(appAPI = APIConfig.getInstance().appService)
        )
        val asteroidRepository = AsteroidRepositoryImpl(
            asteroidDataSource = AsteroidDataSourceImpl(
                AppDatabase.getInstance(applicationContext).getAsteroidDao()
            )
        )
        val dateFormat = SimpleDateFormat(HomeViewModel.DATE_FORMAT, Locale.getDefault())
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
                Result.success()
            }

            is ResultAPI.ERROR -> Result.retry()
        }
    }

    private fun getDate(dateFormat: SimpleDateFormat): Pair<String, String> {
        val now = Calendar.getInstance()
        val startDate = dateFormat.format(now.time)

        now.add(Calendar.DAY_OF_YEAR, 7)
        val endDate = dateFormat.format(now.time)
        return Pair(startDate, endDate)
    }
}