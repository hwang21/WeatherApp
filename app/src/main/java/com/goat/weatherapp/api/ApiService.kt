package com.goat.weatherapp.api

import com.goat.weatherapp.model.Weather
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("forecast/{key}/{latitude},{longitude}")
    suspend fun getWeather(
        @Path("key") key: String,
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double,
        @Query("units") units: String,
        @Query("exclude") exclude: String
    ): Weather

}