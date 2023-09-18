package com.duongvn.asteroidradar.application

import android.app.Application
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.duongvn.asteroidradar.work.RefreshDataWorker
import java.util.concurrent.TimeUnit

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val uploadWorkRequest: WorkRequest =
            PeriodicWorkRequestBuilder<RefreshDataWorker>(24, TimeUnit.HOURS)
                .build()

        WorkManager
            .getInstance(applicationContext)
            .enqueue(uploadWorkRequest)
    }
}