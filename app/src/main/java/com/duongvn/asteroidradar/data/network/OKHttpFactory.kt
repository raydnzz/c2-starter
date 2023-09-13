package com.duongvn.asteroidradar.data.network

import com.duongvn.asteroidradar.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Protocol
import java.util.Collections
import java.util.concurrent.TimeUnit

object OKHttpFactory {
    private const val TIME_OUT = 10L
    private var INSTANCE: OkHttpClient? = null
    private const val API_KEY_PARAM = "api_key"
    private const val API_KEY_VALUE = "yke3D2FbJn4h1Zpdz783ru6DG7FSsqgGTm6HPQe8"

    fun initClient(): OkHttpClient = INSTANCE ?: createClient().also {
        INSTANCE = it
    }

    private fun createClient() = OkHttpClient.Builder()
        .addInterceptor {
            val urlRequest = it.request().url().newBuilder()
                .addQueryParameter(API_KEY_PARAM, API_KEY_VALUE)
                .build()
            it.proceed(
                it.request().newBuilder()
                    .url(urlRequest)
                    .build()
            )
        }
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .protocols(Collections.singletonList(Protocol.HTTP_1_1))
        .build()
}