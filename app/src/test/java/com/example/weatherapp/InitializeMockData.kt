package com.example.weatherapp

import com.example.weatherapp.common.Constants
import com.example.weatherapp.domain.model.remote.*
import com.example.weatherapp.util.getLocaleTemperatureUnit
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.collections.HashMap

private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

val random
    get() = ThreadLocalRandom.current()


fun generateCityWeather(): CityWeather =
    CityWeather(
        city = generateCityInfo(),
        list = generateWeatherForecastList()
    )

private fun generateCityInfo(): CityInfo =
    CityInfo(
        coord = Coord(randomDouble(), randomDouble()),
        country = randomString(),
        id = randomInt(),
        name = randomString(),
        population = randomInt(),
        timezone = randomInt()
    )

private fun generateTemp(): Temp {
    return Temp(
        day = randomDouble(),
        evening = randomDouble(),
        max = randomDouble(),
        min = randomDouble(),
        morning = randomDouble(),
        night = randomDouble()
    )
}

private fun generateWeatherInfo(): WeatherInfo =
    WeatherInfo(
        description = randomString(),
        icon = randomString(),
        id = randomInt(),
        main = randomString()
    )

fun generateWeatherForecastList(
    size: Int = randomPositiveInt(10),
    creationFunction: (Int) -> WeatherForecast = { generateWeatherForecast() }
): List<WeatherForecast> = (0..size).map { creationFunction(it) }

fun generateWeatherForecast(): WeatherForecast =
    WeatherForecast(
        dateTime = randomInt(),
        humidity = randomInt(),
        pressure = randomInt(),
        temp = generateTemp(),
        weather = generateWeatherInfoList()
    )

fun generateWeatherInfoList(
    size: Int = randomPositiveInt(10),
    creationFunction: (Int) -> WeatherInfo = { generateWeatherInfo() }
): List<WeatherInfo> = (0..size).map { creationFunction(it) }

fun randomPositiveInt(maxInt: Int = Int.MAX_VALUE - 1): Int =
    random.nextInt(maxInt + 1).takeIf { it > 0 } ?: randomPositiveInt(maxInt)

fun randomInt() = random.nextInt()
fun randomDouble() = random.nextDouble()
fun randomString(size: Int = 20): String = (0..size)
    .map { charPool[random.nextInt(0, charPool.size)] }
    .joinToString("")

fun generateQueries():HashMap<String,String>{
        val queries: HashMap<String, String> = HashMap()
        queries[Constants.QUERY_CITY_NAME] = "saigon"
        queries[Constants.QUERY_DAY_NUM] = Constants.DEFAULT_FORECAST_DAY
        queries[Constants.QUERY_APP_ID] = Constants.API_KEY
        queries[Constants.QUERY_UNITS] = Locale.getDefault().getLocaleTemperatureUnit().name
        return queries
}