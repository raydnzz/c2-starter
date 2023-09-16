package com.duongvn.asteroidradar.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.duongvn.asteroidradar.data.database.AppDatabase
import com.duongvn.asteroidradar.data.database.entity.Asteroid
import com.duongvn.asteroidradar.data.datasource.apod.ApodDataSourceImpl
import com.duongvn.asteroidradar.data.datasource.asteroid.AsteroidDataSourceImpl
import com.duongvn.asteroidradar.data.network.APIConfig
import com.duongvn.asteroidradar.data.network.result.ResultAPI
import com.duongvn.asteroidradar.data.network.wapi.apod.Apod
import com.duongvn.asteroidradar.data.repositores.apod.ApodRepositoryImpl
import com.duongvn.asteroidradar.data.repositores.asteroid.AsteroidRepositoryImpl
import com.duongvn.asteroidradar.domain.home.AsteroidUseCase
import com.duongvn.asteroidradar.domain.home.AsteroidUseCaseImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeViewModel(
    private val asteroidUseCase: AsteroidUseCase
) : ViewModel() {
    private val _typeShow = MutableStateFlow(TypeShow.ALL)
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private val _imageResult = MutableLiveData<Apod>()
    val imageResult: LiveData<Apod> = _imageResult

    private val _FullAsteroiList = MutableLiveData<List<Asteroid>>()

    private val _AsteroiList = MutableLiveData<List<Asteroid>>()
    val asteroiList: LiveData<List<Asteroid>> = _AsteroiList

    fun setTypeShow(typeShow: TypeShow) {
        _typeShow.value = typeShow
        _FullAsteroiList.value?.let {
            _AsteroiList.value = getAsteroiByFilter(it)
        }
    }

    fun fetchAsteroi() {
        viewModelScope.launch {
            val responseLocal = async { asteroidUseCase.executeGetAsteroidFromLocal() }
            val asteroids = responseLocal.await().first()

            _FullAsteroiList.value = asteroids
            _AsteroiList.value = getAsteroiByFilter(asteroids)
        }
    }

    private fun getAsteroiByFilter(asteroids: List<Asteroid>) = when (_typeShow.value) {
        TypeShow.ALL -> asteroids
        TypeShow.TODAY -> asteroids.filter {
            it.closeApproachDate == dateFormat.format(Calendar.getInstance().time)
        }

        TypeShow.WEEK -> asteroids.filter {
            checkWeek(it.closeApproachDate)
        }
    }

    private fun checkWeek(dateString: String): Boolean {
        val date = dateFormat.parse(dateString) ?: return false
        val now = Calendar.getInstance()
        if (date.before(now.time)) return false
        now.add(Calendar.DAY_OF_YEAR, 7)
        if (date.after(now.time)) return false
        return true
    }

    fun fetchImageOfDay() {
        viewModelScope.launch {
            val result = async { asteroidUseCase.executeGetApod() }
            result.await().also {
                when (it) {
                    is ResultAPI.SUCCESS -> {
                        _imageResult.value = it.data
                    }

                    is ResultAPI.ERROR -> {
                        Log.e("TAG", "fetchImageOfDay: ${it.message}")
                    }
                }
            }
        }
    }

    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd"
        val FACTORY = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as Application)
                val getAsteroidUseCase = AsteroidUseCaseImpl(
                    AsteroidRepositoryImpl(
                        asteroidDataSource = AsteroidDataSourceImpl(
                            AppDatabase.getInstance(application.applicationContext).getAsteroidDao()
                        )
                    ),
                    ApodRepositoryImpl(
                        apodDataSource = ApodDataSourceImpl(
                            APIConfig.getInstance().appService
                        )
                    )
                )
                HomeViewModel(getAsteroidUseCase)
            }
        }
    }

    enum class TypeShow {
        ALL, TODAY, WEEK;
    }
}