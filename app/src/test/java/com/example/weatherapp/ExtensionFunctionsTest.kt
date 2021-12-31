package com.example.weatherapp

import com.example.weatherapp.common.TempUnit
import com.example.weatherapp.util.getLocaleTemperatureUnit
import com.example.weatherapp.util.verifySearchText
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

@RunWith(JUnit4::class)
class ExtensionFunctionsTest {
    @Test
    fun whenUserNotInputCityName_thenFalse(){
        var cityName = ""
        var result = cityName.verifySearchText()
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun whenUserInputLessThanThreeChar_thenFalse(){
        var cityName = "sa"
        var result = cityName.verifySearchText()
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun whenUserInputValidName_thenTrue(){
        var cityName = "saigon"
        var result = cityName.verifySearchText()
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun whenLocaleIsUS_thenUnitIsImperial(){
        var locale = Locale.US
        var unit = locale.getLocaleTemperatureUnit().name
        assertThat(unit).isEqualTo(TempUnit.Imperial.name)
    }

    @Test
    fun whenLocaleIsNotUS_thenUnitIsMetric(){
        var locale = Locale.CHINA
        var unit = locale.getLocaleTemperatureUnit().name
        assertThat(unit).isEqualTo(TempUnit.Metric.name)
    }
}