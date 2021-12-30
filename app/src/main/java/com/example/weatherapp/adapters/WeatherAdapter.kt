package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.WeatherRowLayoutBinding
import com.example.weatherapp.model.local.FormattedCityWeather
import com.example.weatherapp.model.local.FormattedWeatherForecast
import com.example.weatherapp.util.WeatherDiffUtil

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private var weatherForecasts = emptyList<FormattedWeatherForecast>()


    class ViewHolder(private val binding: WeatherRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(formattedWeatherForecast: FormattedWeatherForecast) {
            binding.weatherForecast = formattedWeatherForecast
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WeatherRowLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentWeatherForecast = weatherForecasts[position]
        holder.bind(currentWeatherForecast)
    }

    override fun getItemCount(): Int {
        return weatherForecasts.size
    }

    fun setData(newData: FormattedCityWeather) {
        val weatherDiffUtil =
            WeatherDiffUtil(weatherForecasts, newData.weatherForeCast)
        val diffUtilResult = DiffUtil.calculateDiff(weatherDiffUtil)
        weatherForecasts = newData.weatherForeCast
        diffUtilResult.dispatchUpdatesTo(this)
    }
}