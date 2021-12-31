package com.example.weatherapp.domain.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Temp(
    @SerializedName("day")
    val day: Double,

    @SerializedName("night")
    val night: Double,

    @SerializedName("eve")
    val evening: Double,

    @SerializedName("morn")
    val morning: Double,

    @SerializedName("min")
    val min: Double,

    @SerializedName("max")
    val max: Double,
):Parcelable
