package com.duongvn.asteroidradar.data.repositores.apod

import com.duongvn.asteroidradar.data.network.result.ResultAPI
import com.duongvn.asteroidradar.data.network.wapi.apod.Apod

fun interface ApodRepository {
    suspend fun getResultApod(): ResultAPI<Apod>
}