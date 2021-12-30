package com.example.weatherapp.data.network

import com.example.weatherapp.model.remote.CityWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApi {
    @GET("/data/2.5/forecast/daily")
    suspend fun getCityWeather(
        @QueryMap queries: Map<String, String>
    ): Response<CityWeather>

}