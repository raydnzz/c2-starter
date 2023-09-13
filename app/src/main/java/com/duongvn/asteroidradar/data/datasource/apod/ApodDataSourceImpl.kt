package com.duongvn.asteroidradar.data.datasource.apod

import com.duongvn.asteroidradar.data.network.AppAPI
import com.duongvn.asteroidradar.data.network.service.ApiServiceImpl
import com.duongvn.asteroidradar.data.network.wapi.apod.Apod

class ApodDataSourceImpl(private val appAPI: AppAPI) : ApodDataSource {
    override suspend fun getApod(): Apod =
        ApiServiceImpl(call = appAPI.getApod()).fetch()
}