package com.example.weatherapp.presentation.weather_list

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Constants
import com.example.weatherapp.common.NetworkResult
import com.example.weatherapp.domain.model.local.FormattedCityWeather
import com.example.weatherapp.domain.usecases.GetWeatherFromAPIUseCase
import com.example.weatherapp.domain.usecases.GetWeatherFromLocalDbUseCase
import com.example.weatherapp.domain.usecases.InsertWeatherToLocalDbUseCase
import com.example.weatherapp.util.getLocaleTemperatureUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.collections.set

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val getWeatherFromLocalDbUseCase: GetWeatherFromLocalDbUseCase,
    private val getWeatherFromAPIUseCase: GetWeatherFromAPIUseCase,
    private val insertWeatherToLocalDbUseCase: InsertWeatherToLocalDbUseCase,
    application: Application,
) : AndroidViewModel(application) {

    private val _viewState = MutableLiveData<WeatherViewState>()
    val viewState: LiveData<WeatherViewState>
        get() = _viewState

    init {
        getCityWeatherFromLocal()
    }

    private fun getCityWeather(queries: Map<String, String>) =
        viewModelScope.launch(Dispatchers.IO) {
            if (hasInternetConnection()) {
                getWeatherFromAPIUseCase.invoke(queries).collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            emitUIState(DataSource.Remote, result)
                            val formattedCityWeather = result.data
                            if (formattedCityWeather != null) {
                                offlineCacheCityWeather(formattedCityWeather)
                            }
                        }

                        is NetworkResult.Error -> {
                            emitUIState(DataSource.Remote, result)
                        }

                        is NetworkResult.Loading -> {
                            emitUIState(DataSource.Remote, result)
                        }
                    }
                }
            } else {
                emitUIState(DataSource.Remote, NetworkResult.Error("No internet connection"))
            }
        }

    private fun offlineCacheCityWeather(formattedCityWeather: FormattedCityWeather) =
        viewModelScope.launch(Dispatchers.IO) {
            insertWeatherToLocalDbUseCase.invoke(formattedCityWeather)
        }

    private fun getCityWeatherFromLocal() = viewModelScope.launch(Dispatchers.IO) {
        val result = getWeatherFromLocalDbUseCase.invoke()
        if (result.isNotEmpty()) {
            val formattedCityWeather = result[0].formattedCityWeather
            emitUIState(DataSource.Local, NetworkResult.Success(formattedCityWeather))
        }
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

    private fun applyQueries(cityName: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[Constants.QUERY_CITY_NAME] = cityName
        queries[Constants.QUERY_DAY_NUM] = Constants.DEFAULT_FORECAST_DAY
        queries[Constants.QUERY_APP_ID] = Constants.API_KEY
        queries[Constants.QUERY_UNITS] = Locale.getDefault().getLocaleTemperatureUnit().name
        return queries
    }

    fun btnGetWeatherClicked(cityName: String) {
        getCityWeather(applyQueries(cityName))
    }

    private fun emitUIState(
        source: DataSource,
        result: NetworkResult<FormattedCityWeather>
    ) {
        val viewState = WeatherViewState(
            source = source,
            result = result
        )
        _viewState.postValue(viewState)
    }
}