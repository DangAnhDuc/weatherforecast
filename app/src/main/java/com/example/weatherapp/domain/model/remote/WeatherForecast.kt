package com.example.weatherapp.domain.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherForecast(
    @SerializedName("dt")
    val dateTime: Int,

    @SerializedName("pressure")
    val pressure: Int,

    @SerializedName("humidity")
    val humidity: Int,

    @SerializedName("weather")
    val weather: List<WeatherInfo>,

    @SerializedName("temp")
    val temp: Temp,
):Parcelable
