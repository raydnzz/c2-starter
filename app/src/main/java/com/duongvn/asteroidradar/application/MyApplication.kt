package com.duongvn.asteroidradar.application

import android.app.Application
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.duongvn.asteroidradar.work.RefreshDataWorker

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val uploadWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<RefreshDataWorker>()
                .build()

        WorkManager
            .getInstance(applicationContext)
            .enqueue(uploadWorkRequest)
    }
}