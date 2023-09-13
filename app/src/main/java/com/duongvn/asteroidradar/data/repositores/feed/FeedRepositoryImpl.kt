package com.duongvn.asteroidradar.data.repositores.feed

import com.duongvn.asteroidradar.data.datasource.feed.FeedDataSource
import com.duongvn.asteroidradar.data.network.result.ResultAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FeedRepositoryImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val feedDataSource: FeedDataSource
) : FeedRepository {
    override suspend fun getResultFeed(startDate: String, endDate: String) =
        withContext(dispatcher) {
            try {
                feedDataSource.getResponseFeed(startDate, endDate).let {
                    ResultAPI.SUCCESS(it)
                }
            } catch (e: Exception) {
                ResultAPI.ERROR(e.message ?: "Call API Error")
            }
        }
}