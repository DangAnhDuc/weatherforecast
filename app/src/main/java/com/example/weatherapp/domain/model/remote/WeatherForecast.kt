package com.example.weatherapp.domain.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherForecast(
    @SerializedName("dt")
    val dateTime: Long,

    @SerializedName("pressure")
    val pressure: Long,

    @SerializedName("humidity")
    val humidity: Int,

    @SerializedName("weather")
    val weather: List<WeatherDes>,

    @SerializedName("temp")
    val temp: Temp,
):Parcelable
