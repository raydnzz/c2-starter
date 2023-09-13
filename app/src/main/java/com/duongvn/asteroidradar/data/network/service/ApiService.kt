package com.duongvn.asteroidradar.data.network.service

interface ApiService<T> {
    suspend fun fetch() : T
    fun cancel()
}