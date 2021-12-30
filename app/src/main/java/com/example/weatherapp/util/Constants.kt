package com.example.weatherapp.util

class Constants {
    companion object {
        const val BASE_URL = "https://api.openweathermap.org"
        const val API_KEY = "60c6fbeb4b93ac653c492ba806fc346d"

        //API Query Keys
        const val QUERY_CITY_NAME = "q"
        const val QUERY_DAY_NUM = "cnt"
        const val QUERY_APP_ID = "appid"
        const val QUERY_UNITS = "units"

        //Default Query value
        const val DEFAULT_CITY_NAME = "saigon"
        const val DEFAULT_FORECAST_DAY = "7"
        const val MINIMUM_SEARCH_LENGHT = 3

        //ROOM Database
        const val DATABASE_NAME = "weather_database"
        const val WEATHER_TABLE = "weather_table"

        //Time
        const val DATE_FORMAT_PATTERN = "EEE, MMM d yyyy"
        const val ONE_SECOND_IN_MILLIS = 1000L
        const val ONE_MINUTE_IN_SECONDS = 60
        const val ONE_HOUR_IN_SECONDS = 60
        const val ONE_DAY_IN_SECONDS = 24 * ONE_HOUR_IN_SECONDS * ONE_MINUTE_IN_SECONDS
    }
}