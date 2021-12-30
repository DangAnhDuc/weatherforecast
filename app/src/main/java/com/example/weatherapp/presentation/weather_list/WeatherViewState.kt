package com.example.weatherapp.presentation.weather_list

import com.example.weatherapp.domain.model.local.FormattedCityWeather
import com.example.weatherapp.common.NetworkResult

data class WeatherViewState(
    val source: DataSource,
    val result: NetworkResult<FormattedCityWeather>?,
)