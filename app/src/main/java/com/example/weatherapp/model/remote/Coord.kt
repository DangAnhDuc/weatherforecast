package com.example.weatherapp.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coord(
    @SerializedName("lat")
    val lat: Double,

    @SerializedName("long")
    val lon: Double
):Parcelable