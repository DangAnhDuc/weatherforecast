package com.example.weatherapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.domain.model.local.FormattedCityWeather
import com.example.weatherapp.common.Constants.Companion.WEATHER_TABLE

@Entity(tableName = WEATHER_TABLE)
class WeatherEntity(var formattedCityWeather: FormattedCityWeather) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}