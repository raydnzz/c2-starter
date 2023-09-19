package com.duongvn.asteroidradar.application

import android.annotation.SuppressLint
import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.duongvn.asteroidradar.work.RefreshDataWorker
import java.util.concurrent.TimeUnit

class MyApplication : Application() {

    @SuppressLint("IdleBatteryChargingConstraints")
    override fun onCreate() {
        super.onCreate()

        val constrains = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .setRequiresDeviceIdle(true)
            .build()

        val uploadWorkRequest: WorkRequest =
            PeriodicWorkRequestBuilder<RefreshDataWorker>(24, TimeUnit.HOURS)
                .setConstraints(constraints = constrains)
                .build()

        WorkManager
            .getInstance(applicationContext)
            .enqueue(uploadWorkRequest)
    }
}