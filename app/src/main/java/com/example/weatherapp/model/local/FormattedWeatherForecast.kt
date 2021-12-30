package com.example.weatherapp.model.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FormattedWeatherForecast(
    val date: String,
    val avgTemp: String,
    val pressure: String,
    val humidity: String,
    val description: String
):Parcelable