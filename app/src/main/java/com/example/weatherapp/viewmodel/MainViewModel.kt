package com.example.weatherapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.weatherapp.data.database.entities.WeatherEntity
import com.example.weatherapp.data.repository.Repository
import com.example.weatherapp.model.local.FormattedCityWeather
import com.example.weatherapp.model.remote.CityWeather
import com.example.weatherapp.util.Constants
import com.example.weatherapp.util.NetworkResult
import com.example.weatherapp.util.convertWeatherDataViewModel
import com.example.weatherapp.util.getLocaleTemperatureUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*
import kotlin.collections.HashMap

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application,
) : AndroidViewModel(application) {
    /** ROOM DATABASE */
    val readCityWeather: LiveData<List<WeatherEntity>> =
        repository.local.readCityWeather().asLiveData()

    private fun insertCityWeather(recipesEntity: WeatherEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertCityWeather(recipesEntity)
        }

    /** RETROFIT */
    var cityWeatherResponse: MutableLiveData<NetworkResult<FormattedCityWeather>> =
        MutableLiveData()

    fun getCityWeather(queries: Map<String, String>) = viewModelScope.launch {
        getCityWeatherSafeCall(queries)
    }

    private suspend fun getCityWeatherSafeCall(queries: Map<String, String>) {
        cityWeatherResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getCityWeather(queries)
                cityWeatherResponse.value = handleCityWeatherResponse(response)

                val formattedCityWeather = cityWeatherResponse.value!!.data
                if (formattedCityWeather != null) {
                    offlineCacheCityWeather(formattedCityWeather)
                }
            } catch (e: Exception) {
                cityWeatherResponse.value = NetworkResult.Error("Weather not found")
            }
        } else {
            cityWeatherResponse.value = NetworkResult.Error("No internet connection")
        }
    }

    private fun handleCityWeatherResponse(response: Response<CityWeather>): NetworkResult<FormattedCityWeather> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.list.isNullOrEmpty() -> {
                return NetworkResult.Error("Weather not found.")
            }
            response.isSuccessful -> {
                val formattedCityWeather = response.body().convertWeatherDataViewModel()
                return NetworkResult.Success(formattedCityWeather!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun offlineCacheCityWeather(formattedCityWeather: FormattedCityWeather) {
        val cityWeatherEntity = WeatherEntity(formattedCityWeather)
        insertCityWeather(cityWeatherEntity)
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun applyQueries(cityName: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[Constants.QUERY_CITY_NAME] = cityName
        queries[Constants.QUERY_DAY_NUM] = Constants.DEFAULT_FORECAST_DAY
        queries[Constants.QUERY_APP_ID] = Constants.API_KEY
        queries[Constants.QUERY_UNITS] = Locale.getDefault().getLocaleTemperatureUnit().name
        return queries
    }
}