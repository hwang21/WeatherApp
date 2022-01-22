package com.goat.weatherapp.repository

import com.goat.weatherapp.api.ApiService
import kotlinx.coroutines.flow.flow

class Repository(private val apiService: ApiService) {

    fun getWeather() = flow {
        emit(
            apiService.getWeather(
            "d76cdb142a6138e1a2333ce89e5ee4c0",
            37.8267,
            -122.4233,
                "us",
                arrayListOf("minutely", "alerts", "flags").toString()
            )
        )
    }

}