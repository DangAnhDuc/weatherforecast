package com.example.weatherapp.model.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormattedCityWeather(
    val cityName: String,
    val weatherForeCast: List<FormattedWeatherForecast>
) : Parcelable