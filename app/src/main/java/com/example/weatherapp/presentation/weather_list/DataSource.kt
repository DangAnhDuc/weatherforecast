package com.example.weatherapp.presentation.weather_list

import androidx.annotation.Keep

@Keep
enum class DataSource(val source: String) {
    Local("local"),
    Remote("remote"),
}