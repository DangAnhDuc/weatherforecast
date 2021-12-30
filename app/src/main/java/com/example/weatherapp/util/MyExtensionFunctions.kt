package com.example.weatherapp.util

import android.text.format.DateFormat
import com.example.weatherapp.common.Constants.Companion.DATE_FORMAT_PATTERN
import com.example.weatherapp.common.Constants.Companion.MINIMUM_SEARCH_LENGHT
import com.example.weatherapp.common.Constants.Companion.ONE_SECOND_IN_MILLIS
import com.example.weatherapp.common.TempUnit
import com.example.weatherapp.domain.model.local.FormattedCityWeather
import com.example.weatherapp.domain.model.local.FormattedWeatherForecast
import com.example.weatherapp.domain.model.remote.CityWeather
import java.util.*

fun CityWeather?.convertWeatherDataViewModel(): FormattedCityWeather {
    val forecastList = mutableListOf<FormattedWeatherForecast>()
    this?.list?.forEach { weatherForecast ->
        val dateInMillis = weatherForecast.dateTime * ONE_SECOND_IN_MILLIS
        val date = Date(dateInMillis)
        val formattedDateString = DateFormat.format(DATE_FORMAT_PATTERN, date).toString()
        val avgTempString = weatherForecast.temp.let {
            val avgTemp = calculateAvgTemperature(it.day, it.evening, it.morning, it.night)
            val unit = Locale.getDefault().getLocaleTemperatureUnit().unit
            "$avgTemp $unit"
        }
        val pressureString = weatherForecast.pressure.toString()
        val humidityString = weatherForecast.humidity.toString() + "%"
        val descriptionString =
            if (weatherForecast.weather.isEmpty()) "" else weatherForecast.weather[0].description

        forecastList.add(
            FormattedWeatherForecast(
                date = formattedDateString,
                avgTemp = avgTempString,
                pressure = pressureString,
                humidity = humidityString,
                description = descriptionString
            )
        )
    }
    var cityName = ""
    this?.city?.name?.let {
        cityName = it
    }
    return FormattedCityWeather(cityName = cityName, weatherForeCast = forecastList)
}

private fun calculateAvgTemperature(vararg temperature: Float): Int = temperature.average().toInt()

fun String.verifySearchText(): Boolean {
    return this.length >= MINIMUM_SEARCH_LENGHT
}

fun Locale.getLocaleTemperatureUnit(): TempUnit {
    return when (this) {
        Locale.US -> TempUnit.Imperial
        else -> TempUnit.Metric
    }
}
