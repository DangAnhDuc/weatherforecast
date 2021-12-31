package com.example.weatherapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.database.entities.WeatherEntity

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCityWeather(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM weather_table ORDER BY id ASC ")
    fun readCityWeather(): List<WeatherEntity>
}