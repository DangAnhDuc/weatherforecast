package com.example.weatherapp.data.repository

import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.model.remote.CityWeather
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val weatherApi: WeatherApi
) {
    suspend fun getCityWeather(queries: Map<String, String>): Response<CityWeather> {
        return weatherApi.getCityWeather(queries)
    }
}