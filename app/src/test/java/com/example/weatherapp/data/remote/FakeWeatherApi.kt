package com.example.weatherapp.data.remote

import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.domain.model.remote.CityWeather
import retrofit2.Response

class FakeWeatherApi: WeatherApi {

    private var cityWeather: CityWeather?=null

    fun initCityWeather(cityWeather: CityWeather){
        this.cityWeather = cityWeather
    }

    override suspend fun getCityWeather(queries: Map<String, String>): Response<CityWeather> {
        return Response.success(cityWeather)
    }
}