package com.example.weatherapp.domain.usecases

import com.example.weatherapp.data.database.entities.WeatherEntity
import com.example.weatherapp.data.repository.Repository
import com.example.weatherapp.domain.model.local.FormattedCityWeather
import javax.inject.Inject

class InsertWeatherToLocalDbUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(formattedCityWeather: FormattedCityWeather) {
        offlineCacheCityWeather(formattedCityWeather)
    }

    private suspend fun insertCityWeather(recipesEntity: WeatherEntity) {
        repository.local.insertCityWeather(recipesEntity)
    }

    private suspend fun offlineCacheCityWeather(formattedCityWeather: FormattedCityWeather) {
        val cityWeatherEntity = WeatherEntity(formattedCityWeather)
        insertCityWeather(cityWeatherEntity)
    }
}