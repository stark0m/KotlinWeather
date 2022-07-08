package com.example.kotlinweather.lesson6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kotlinweather.databinding.WeatherOneCityShowDialogBinding
import com.example.kotlinweather.domain.TAG_WEATHER_TO_SHOW
import com.example.kotlinweather.domain.Weather
import java.io.IOException


class Lesson6Fragment : Fragment() {
    private var _binding: WeatherOneCityShowDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherToShow: Weather
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            val weatherFromBundle = it.getParcelable<Weather>(TAG_WEATHER_TO_SHOW)
            weatherToShow = weatherFromBundle
                ?: throw IOException("Не получен бандл с городом для отображения погоды ")
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRecievedWeather(weatherToShow)

    }






    private fun showRecievedWeather(weather: Weather) {
        weather.let { it_weather ->
            binding.apply {
                cityName.text = it_weather.city.name
                temperatureValue.text = it_weather.temperature.toString()
                feelsLikeValue.text = it_weather.feelsLike.toString()
                cityCoordinates.text = "${it_weather.city.lat}/${it_weather.city.lon}"
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WeatherOneCityShowDialogBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object {

        @JvmStatic
        fun newInstance(weather: Weather) =
            Lesson6Fragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TAG_WEATHER_TO_SHOW, weather)

                }
            }
    }
}