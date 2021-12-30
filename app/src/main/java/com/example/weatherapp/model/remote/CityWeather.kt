package com.example.weatherapp.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityWeather(
    @SerializedName("city")
    val city: CityInfo,

    @SerializedName("list")
    val list: List<WeatherForecast>
):Parcelable
