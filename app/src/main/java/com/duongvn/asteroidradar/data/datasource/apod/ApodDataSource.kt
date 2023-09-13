package com.duongvn.asteroidradar.data.datasource.apod

import com.duongvn.asteroidradar.data.network.wapi.apod.Apod

fun interface ApodDataSource {
    suspend fun getApod(): Apod
}