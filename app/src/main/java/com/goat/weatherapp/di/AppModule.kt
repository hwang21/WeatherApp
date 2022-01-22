package com.goat.weatherapp.di

import com.goat.weatherapp.api.ApiService
import com.goat.weatherapp.model.HourlyData
import com.goat.weatherapp.repository.Repository
import com.goat.weatherapp.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService): Repository {
        return Repository(apiService)
    }

    @Singleton
    @Provides
    fun provideHourlyData(): List<HourlyData> {
        return emptyList()
    }

}