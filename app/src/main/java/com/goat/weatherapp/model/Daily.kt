package com.goat.weatherapp.model

data class Daily(
    val data: List<DailyData>,
    val icon: String,
    val summary: String
)
