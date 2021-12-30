package com.example.weatherapp.domain.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityInfo(
    @SerializedName("coord")
    val coord: Coord,

    @SerializedName("country")
    val country: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("population")
    val population: Int,

    @SerializedName("timezone")
    val timezone: Int
) : Parcelable
