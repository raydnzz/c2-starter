package com.duongvn.asteroidradar.domain.home

import com.duongvn.asteroidradar.data.database.entity.Asteroid
import com.duongvn.asteroidradar.data.network.result.ResultAPI
import com.duongvn.asteroidradar.data.network.wapi.apod.Apod
import kotlinx.coroutines.flow.Flow

interface AsteroidUseCase {
    fun executeGetAsteroidFromLocal(): Flow<List<Asteroid>>
    suspend fun executeGetApod(): ResultAPI<Apod>
}