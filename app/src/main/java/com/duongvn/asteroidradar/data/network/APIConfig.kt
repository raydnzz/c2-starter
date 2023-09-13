package com.duongvn.asteroidradar.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIConfig private constructor() {

    val appService: AppAPI

    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(OKHttpFactory.initClient())
            .build()

        appService = retrofit.create(AppAPI::class.java)
    }

    companion object {
        private const val BASE_URL = "https://api.nasa.gov"

        private var INSTANCE: APIConfig? = null

        fun getInstance(): APIConfig = INSTANCE ?: APIConfig().also {
            INSTANCE = it
        }
    }
}