package com.example.weatherapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.adapters.WeatherAdapter
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.util.NetworkResult
import com.example.weatherapp.util.RootChecker
import com.example.weatherapp.util.observeOnce
import com.example.weatherapp.util.verifySearchText
import com.example.weatherapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var _binding: ActivityMainBinding
    private val mAdapter by lazy { WeatherAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)

        _binding.mainViewModel = mainViewModel
        _binding.lifecycleOwner = this

        if (RootChecker.isRooted()) {
            showAlertDialogAndExitApp()
        }
        setupRecyclerView()

        lifecycleScope.launch {
            readDatabase()
        }

        _binding.btnGetWeather.setOnClickListener {
            val cityNameString = _binding.edtCityName.text.toString()
            if (cityNameString.verifySearchText()) {
                requestApiData(cityNameString)
            } else {
                Toast.makeText(
                    this,
                    "Need at least 3 character to search city",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showAlertDialogAndExitApp() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Alert")
        alertDialog.setMessage("You may run app in unsafe device, please change device")
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { _, _ -> finish() }
        alertDialog.show()
    }

    private fun requestApiData(cityName: String) {
        Log.d(TAG, "requestApiData called!")
        mainViewModel.getCityWeather(mainViewModel.applyQueries(cityName))
        mainViewModel.cityWeatherResponse.observe(this@MainActivity, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    Log.d(TAG, "Load success API")
                    showRecyclerView(true)
                    response.data?.let {
                        mAdapter.setData(it)
                        _binding.cityWeather = it
                    }
                }

                is NetworkResult.Error -> {
                    Log.d(TAG, "Load error API")
                    showRecyclerView(false)
                    Toast.makeText(
                        this,
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    Log.d(TAG, "Loading API")
                }
            }
        })
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readCityWeather.observeOnce(this@MainActivity, { database ->
                if (database.isNotEmpty()) {
                    showRecyclerView(true)
                    database[0].formattedCityWeather.let {
                        mAdapter.setData(it)
                        _binding.cityWeather = it
                    }
                } else {
                    showRecyclerView(false)
                }
            })
        }
    }

    private fun showRecyclerView(hasData: Boolean) {
        if (hasData) {
            _binding.rcWeatherForecast.visibility = View.VISIBLE
            _binding.tvNoData.visibility = View.GONE
        } else {
            _binding.rcWeatherForecast.visibility = View.GONE
            _binding.tvNoData.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView() {
        _binding.rcWeatherForecast.adapter = mAdapter
        _binding.rcWeatherForecast.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                RecyclerView.VERTICAL
            )
        )
        _binding.rcWeatherForecast.layoutManager = LinearLayoutManager(this)
    }
}