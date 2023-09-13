package com.duongvn.asteroidradar.data.network.result

sealed class ResultAPI<out T: Any> {
    data class SUCCESS<out T: Any>(val data: T) : ResultAPI<T>()
    data class ERROR(val message: String) : ResultAPI<Nothing>()
}
