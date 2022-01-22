package com.goat.weatherapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Weather(
    val currently: Currently,
    val daily: Daily,
    val flags: Flags,
    val hourly: Hourly,
    val latitude: Double,
    val longitude: Double,
    val minutely: Minutely,
    val offset: Int,
    val timezone: String
)

data class Flags(
    @SerializedName("nearest-station")
    @Expose
    val nearestStation: Double,
    val sources: List<String>,
    val units: String
)



