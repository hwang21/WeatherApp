package com.goat.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goat.weatherapp.model.HourlyData
import com.goat.weatherapp.model.Weather
import com.goat.weatherapp.repository.Repository
import com.goat.weatherapp.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val weatherState: MutableStateFlow<ResponseState<Weather>>,
    val hourlyWeatherState: MutableStateFlow<List<HourlyData>>,
    private val repository: Repository
) : ViewModel() {

    internal fun getWeather() = viewModelScope.launch {
        weatherState.value = ResponseState.Loading
        repository.getWeather()
            .flowOn(Dispatchers.IO)
            .catch { e ->
                weatherState.value = ResponseState.Error(e.toString())
            }
            .collect {
                weatherState .value = ResponseState.Success(it)
            }
    }
}