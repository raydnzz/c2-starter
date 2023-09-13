package com.duongvn.asteroidradar.data.repositores.feed

import com.duongvn.asteroidradar.data.network.result.ResultAPI
import com.duongvn.asteroidradar.data.network.wapi.feed.ResponseFeed

fun interface FeedRepository {
    suspend fun getResultFeed(startDate: String, endDate: String): ResultAPI<ResponseFeed>
}