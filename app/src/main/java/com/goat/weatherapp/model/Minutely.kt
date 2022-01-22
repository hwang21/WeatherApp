package com.goat.weatherapp.model

data class Minutely(
    val data: List<MinutelyData>,
    val icon: String,
    val summary: String
)
