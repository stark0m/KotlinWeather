package com.example.kotlinweather.view.onecityview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kotlinweather.R
import com.example.kotlinweather.databinding.WeatherOneCityShowDialogBinding
import com.example.kotlinweather.databinding.WeatherShowFragmentBinding
import com.example.kotlinweather.domain.Weather
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * не нашел как надуть binding
 * OneCityWeatherViewDialogBinding нет такого класса
 */
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

        binding.cityName.text = weather.city.name
        binding.temperatureValue.text = weather.temperature.toString()
        binding.feelsLikeValue.text = weather.feelsLike.toString()
        binding.cityCoordinates.text = "${weather.city.lat}/${weather.city.lon}"

    }
    companion object{
        const val TAG="OneCityWeatherViewDialog"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}