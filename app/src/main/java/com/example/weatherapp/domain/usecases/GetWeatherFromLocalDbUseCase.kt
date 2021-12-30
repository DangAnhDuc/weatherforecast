package com.example.weatherapp.domain.usecases

import com.example.weatherapp.data.database.entities.WeatherEntity
import com.example.weatherapp.data.repository.Repository
import javax.inject.Inject

class GetWeatherFromLocalDbUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): List<WeatherEntity> {
        return repository.local.readCityWeather()
    }
}