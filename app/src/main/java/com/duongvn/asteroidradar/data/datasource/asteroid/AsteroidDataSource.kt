package com.duongvn.asteroidradar.data.datasource.asteroid

import com.duongvn.asteroidradar.data.database.entity.Asteroid
import kotlinx.coroutines.flow.Flow

interface AsteroidDataSource {
    suspend fun insert(asteroid: Asteroid)
    suspend fun insertAll(vararg asteroids: Asteroid)
    fun getAsteroids(): Flow<List<Asteroid>>
    suspend fun update(asteroid: Asteroid)
    suspend fun delete(asteroid: Asteroid)
}