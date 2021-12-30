package com.example.weatherapp.domain.usecases

import com.example.weatherapp.data.repository.Repository
import com.example.weatherapp.domain.model.local.FormattedCityWeather
import com.example.weatherapp.domain.model.remote.CityWeather
import com.example.weatherapp.common.NetworkResult
import com.example.weatherapp.util.convertWeatherDataViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class GetWeatherFromAPIUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(queries: Map<String, String>): Flow<NetworkResult<FormattedCityWeather>> =
        flow {
            emit(NetworkResult.Loading())
            try {
                val response = repository.remote.getCityWeather(queries)
                val result = handleCityWeatherResponse(response)
                emit(result)
            } catch (e: HttpException) {
                emit(NetworkResult.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(NetworkResult.Error("Couldn't reach server. Check your internet connection"))
            } catch (e: Exception) {
                emit(NetworkResult.Error("Weather not found"))
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
}