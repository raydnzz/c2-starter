package com.duongvn.asteroidradar.data.repositores.asteroid

import com.duongvn.asteroidradar.data.database.entity.Asteroid
import com.duongvn.asteroidradar.data.datasource.asteroid.AsteroidDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AsteroidRepositoryImpl(
    private val asteroidDataSource: AsteroidDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AsteroidRepository {
    override suspend fun insert(asteroid: Asteroid) = withContext(dispatcher) {
        asteroidDataSource.insert(asteroid)
    }

    override suspend fun insertAll(vararg asteroids: Asteroid) = withContext(dispatcher) {
        asteroidDataSource.insertAll(*asteroids)
    }

    override fun getAsteroids(): Flow<List<Asteroid>> = asteroidDataSource.getAsteroids()

    override suspend fun update(asteroid: Asteroid) = withContext(dispatcher) {
        asteroidDataSource.update(asteroid)
    }

    override suspend fun delete(asteroid: Asteroid) = withContext(dispatcher) {
        asteroidDataSource.delete(asteroid)
    }
}
