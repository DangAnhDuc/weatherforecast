package com.example.weatherapp.data.repository

import com.example.weatherapp.data.database.WeatherDao
import com.example.weatherapp.data.database.entities.WeatherEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val weatherDao: WeatherDao
) {
    fun readCityWeather(): Flow<List<WeatherEntity>> {
        return weatherDao.readCityWeather()
    }

    suspend fun insertCityWeather(weatherEntity: WeatherEntity) {
        weatherDao.insertCityWeather(weatherEntity)
    }
}