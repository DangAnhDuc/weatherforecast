package com.example.weatherapp.domain.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Temp(
    @SerializedName("day")
    val day: Float,

    @SerializedName("night")
    val night: Float,

    @SerializedName("eve")
    val evening: Float,

    @SerializedName("morn")
    val morning: Float,

    @SerializedName("min")
    val min: Float,

    @SerializedName("max")
    val max: Float,
):Parcelable
