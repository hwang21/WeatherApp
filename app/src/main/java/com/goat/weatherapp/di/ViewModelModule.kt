package com.goat.weatherapp.di

import com.goat.weatherapp.model.HourlyData
import com.goat.weatherapp.model.Weather
import com.goat.weatherapp.utils.ResponseState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @ViewModelScoped
    @Provides
    fun provideWeatherStateFlow(): MutableStateFlow<ResponseState<Weather>> {
        return MutableStateFlow(ResponseState.Empty)
    }

    @ViewModelScoped
    @Provides
    fun provideHourlyWeatherStateFlow(): MutableStateFlow<List<HourlyData>> {
        return MutableStateFlow(emptyList())
    }
}