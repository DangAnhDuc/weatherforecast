package com.example.weatherapp.presentation.weather_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.weatherapp.common.NetworkResult
import com.example.weatherapp.databinding.FragmentWeatherListBinding
import com.example.weatherapp.presentation.BindingFragment
import com.example.weatherapp.util.RootChecker
import com.example.weatherapp.util.verifySearchText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherListFragment : BindingFragment<FragmentWeatherListBinding>() {
    companion object {
        private val TAG = WeatherListFragment::class.java.simpleName
    }

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentWeatherListBinding::inflate

    private val weatherListViewModel: WeatherListViewModel by viewModels()
    private val adapter by lazy { WeatherAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        if (RootChecker.isRooted()) {
            showAlertDialogAndExitApp()
        }
        subscribeToStates()
        initEvents()
    }

    private fun initEvents() {
        binding.btnGetWeather.setOnClickListener {
            val cityNameString = binding.edtCityName.text.toString()
            if (cityNameString.verifySearchText()) {
                weatherListViewModel.btnGetWeatherClicked(cityNameString)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Need at least 3 character to search city",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rcWeatherForecast.adapter = adapter
        binding.rcWeatherForecast.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )
        binding.rcWeatherForecast.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun showRecyclerView(hasData: Boolean) {
        if (hasData) {
            binding.rcWeatherForecast.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.GONE
        } else {
            binding.rcWeatherForecast.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
        }
    }

    private fun subscribeToStates() {
        weatherListViewModel.viewState.observe(viewLifecycleOwner, { response ->
            when (val formattedCityWeather = response.result) {
                is NetworkResult.Success -> {
                    Log.d(TAG, "Load success API")
                    showRecyclerView(true)
                    formattedCityWeather.data?.let {
                        adapter.setData(it)
                        binding.cityWeather = it
                    }
                }

                is NetworkResult.Error -> {
                    Log.d(TAG, "Load error API")
                    showRecyclerView(false)
                    Toast.makeText(
                        requireContext(),
                        formattedCityWeather.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    Log.d(TAG, "Loading API")
                }
            }
        })
    }

    private fun showAlertDialogAndExitApp() {
        val alertDialog: AlertDialog = AlertDialog.Builder(requireContext()).create()
        alertDialog.setTitle("Alert")
        alertDialog.setMessage("You may run app in unsafe device, please change device")
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL,
            "OK"
        ) { _, _ -> requireActivity().finish() }
        alertDialog.show()
    }
}