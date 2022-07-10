package com.example.kotlinweather.view.onecityview.dialogfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinweather.databinding.WeatherOneCityShowDialogBinding
import com.example.kotlinweather.domain.Weather
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private var _binding: WeatherOneCityShowDialogBinding? = null
private val binding get() = _binding!!

class OneCityWeatherViewDialog(val weather: Weather) : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WeatherOneCityShowDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            cityName.text = weather.city.name
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = weather.feelsLike.toString()
            cityCoordinates.text = "${weather.city.lat}/${weather.city.lon}"
        }

    }

    companion object {
        const val TAG = "OneCityWeatherViewDialog"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}