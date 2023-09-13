package com.duongvn.asteroidradar.data.network.service

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ApiServiceImpl<T>(private val call: Call<T>): ApiService<T> {
    override suspend fun fetch(): T = suspendCancellableCoroutine {
        it.invokeOnCancellation {
            cancel()
        }
        call.enqueue(object : Callback<T>{
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    response.body()?.also { data ->
                        return it.resume(data)
                    }
                }

                it.resumeWithException(Exception("API Empty"))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                it.resumeWithException(t)
            }
        })
    }

    override fun cancel() {
        call.cancel()
    }
}