package com.duongvn.asteroidradar.domain.home

import com.duongvn.asteroidradar.data.database.entity.Asteroid
import com.duongvn.asteroidradar.data.network.result.ResultAPI
import com.duongvn.asteroidradar.data.network.wapi.apod.Apod
import com.duongvn.asteroidradar.data.repositores.apod.ApodRepository
import com.duongvn.asteroidradar.data.repositores.asteroid.AsteroidRepository
import kotlinx.coroutines.flow.Flow

class AsteroidUseCaseImpl(
    private val asteroidRepository: AsteroidRepository,
    private val apodRepository: ApodRepository
) : AsteroidUseCase {
    override fun executeGetAsteroidFromLocal(): Flow<List<Asteroid>> {
        return asteroidRepository.getAsteroids()
    }

    override suspend fun executeGetApod(): ResultAPI<Apod> {
        return apodRepository.getResultApod()
    }
}