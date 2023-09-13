package com.duongvn.asteroidradar.data.datasource.asteroid

import com.duongvn.asteroidradar.data.database.dao.AsteroidDao
import com.duongvn.asteroidradar.data.database.entity.Asteroid
import kotlinx.coroutines.flow.Flow

class AsteroidDataSourceImpl(private val asteroidDao: AsteroidDao) : AsteroidDataSource {
    override suspend fun insert(asteroid: Asteroid) {
        asteroidDao.insert(asteroid)
    }

    override suspend fun insertAll(vararg asteroids: Asteroid) {
        asteroidDao.insertAll(*asteroids)
    }

    override fun getAsteroids(): Flow<List<Asteroid>> {
        return asteroidDao.getAll()
    }

    override suspend fun update(asteroid: Asteroid) {
        asteroidDao.update(asteroid)
    }

    override suspend fun delete(asteroid: Asteroid) {
        asteroidDao.delete(asteroid)
    }
}
