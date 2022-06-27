package com.example.kotlinweather.view.onecityview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kotlinweather.R
import com.example.kotlinweather.databinding.WeatherShowFragmentBinding
import com.example.kotlinweather.domain.Weather
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * не нашел как надуть binding
 * OneCityWeatherViewDialogBinding нет такого класса
 */
class OneCityWeatherViewDialog(val weather: Weather) : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.weather_one_city_show_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityName : TextView = view.findViewById(R.id.cityName)
        val temperatureValue : TextView = view.findViewById(R.id.temperatureValue)
        val feelsLikeValue : TextView = view.findViewById(R.id.feelsLikeValue)
        val cityCoordinates : TextView = view.findViewById(R.id.cityCoordinates)



        cityName.text = weather.city.name
        temperatureValue.text = weather.temperature.toString()
        feelsLikeValue.text = weather.feelsLike.toString()
        cityCoordinates.text = "${weather.city.lat}/${weather.city.lon}"

    }
    companion object{
        const val TAG="OneCityWeatherViewDialog"
    }
}