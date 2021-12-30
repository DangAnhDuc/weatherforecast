package com.example.weatherapp.util

import androidx.annotation.Keep

@Keep
enum class TempUnit(val unit: String) {
    Kelvin("°K"),
    Metric("°C"),
    Imperial("°F")
}