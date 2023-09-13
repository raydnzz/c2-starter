package com.duongvn.asteroidradar.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.duongvn.asteroidradar.data.database.entity.Asteroid
import com.duongvn.asteroidradar.ui.detail.adapter.DetailAdapter
import com.duongvn.asteroidradar.ui.detail.dto.DetailDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<DetailAdapter.Item>>(listOf())
    val items: LiveData<List<DetailAdapter.Item>>
        get() = _items.asLiveData()

    fun updateListItem(asteroid: Asteroid) {
        viewModelScope.launch {
            _items.update {
                convertToDto(asteroid)
            }
        }
    }

    private fun convertToDto(asteroid: Asteroid): List<DetailAdapter.Item> {
        return listOf(
            DetailAdapter.Item.HEADER(
                isPotentiallyHazardous = asteroid.isPotentiallyHazardous
            ),
            DetailAdapter.Item.NORMAL(
                detailDto = DetailDto(
                    key = "Close approach date",
                    value = asteroid.closeApproachDate
                )
            ),
            DetailAdapter.Item.NORMAL(
                detailDto = DetailDto(
                    key = "Absolute magnitude",
                    value = "${asteroid.absoluteMagnitude} au",
                    isShow = true
                )
            ),
            DetailAdapter.Item.NORMAL(
                detailDto = DetailDto(
                    key = "Estimated diameter",
                    value = "${asteroid.estimatedDiameter} km"
                )
            ),
            DetailAdapter.Item.NORMAL(
                detailDto = DetailDto(
                    key = "Relative velocity",
                    value = "${asteroid.relativeVelocity} km/s"
                )
            ),
            DetailAdapter.Item.NORMAL(
                detailDto = DetailDto(
                    key = "Distance from earth",
                    value = "${asteroid.distanceFromEarth} au"
                )
            )
        )
    }
}