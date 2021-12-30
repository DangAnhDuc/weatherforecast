package com.example.weatherapp.data.database

import androidx.room.TypeConverter
import com.example.weatherapp.model.local.FormattedCityWeather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun formattedCityWeatherToString(formattedCityWeather: FormattedCityWeather): String {
        return gson.toJson(formattedCityWeather)
    }

    @TypeConverter
    fun stringToFormattedCityWeather(data: String): FormattedCityWeather {
        val listType = object : TypeToken<FormattedCityWeather>() {}.type
        return gson.fromJson(data, listType)
    }
}