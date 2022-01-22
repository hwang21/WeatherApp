package com.goat.weatherapp.model

data class Hourly(
    val data: List<HourlyData>,
    val icon: String,
    val summary: String
)
