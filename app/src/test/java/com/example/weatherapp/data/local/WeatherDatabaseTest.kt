package com.example.weatherapp.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.example.weatherapp.data.database.WeatherDao
import com.example.weatherapp.data.database.WeatherDatabase
import com.example.weatherapp.data.database.entities.WeatherEntity
import com.example.weatherapp.generateCityWeather
import com.example.weatherapp.util.convertToFormattedCityWeather
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class WeatherDatabaseTest {
    private lateinit var weatherDatabase: WeatherDatabase
    private lateinit var dao: WeatherDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        this.weatherDatabase = Room.inMemoryDatabaseBuilder(
            context,
            WeatherDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        this.dao= weatherDatabase.weatherDao()
    }

    @After
    fun closeDb() {
        weatherDatabase.close()
    }

    @Test
    fun insertAndReadCityWeather() = runBlocking{
        val cityWeather = generateCityWeather()
        val formattedCityWeather = cityWeather.convertToFormattedCityWeather()
        dao.insertCityWeather(WeatherEntity(formattedCityWeather))

        val savedCityWeather = dao.readCityWeather()
        assertThat(savedCityWeather[0].equals(formattedCityWeather))
    }
}