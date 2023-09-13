package com.duongvn.asteroidradar.data.datasource.feed

import com.duongvn.asteroidradar.data.network.wapi.feed.ResponseFeed

fun interface FeedDataSource {
    suspend fun getResponseFeed(startDate: String, endDate: String): ResponseFeed
}