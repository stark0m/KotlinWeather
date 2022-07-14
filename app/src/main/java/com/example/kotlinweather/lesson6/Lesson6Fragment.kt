package com.example.kotlinweather.lesson6

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kotlinweather.databinding.WeatherOneCityShowDialogBinding
import com.example.kotlinweather.domain.BROADCAST_INTENT_FILTER
import com.example.kotlinweather.domain.TAG_WEATHER_TO_SHOW
import com.example.kotlinweather.domain.WEATHET_DTO_KEY
import com.example.kotlinweather.domain.Weather
import com.google.android.material.snackbar.Snackbar
import java.io.IOException


class Lesson6Fragment : Fragment() {
    private var _binding: WeatherOneCityShowDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherToShow: Weather

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            p1?.let {

                val receivetWeather: Weather? = it.getParcelableExtra(WEATHET_DTO_KEY)
                receivetWeather?.let {
                    showRecievedWeather(it)
                }
            }
        }

    }


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
        startWeatherService(weatherToShow)
        registerBCReceiver()

    }

    private fun registerBCReceiver() {
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(broadcastReceiver, IntentFilter(BROADCAST_INTENT_FILTER))
    }

    private fun startWeatherService(weatherToShow: Weather) {
        requireContext().startService(Intent(requireContext(), WeatherService::class.java).apply {
            putExtra(WEATHET_DTO_KEY, weatherToShow)
        })
    }


    private fun showRecievedWeather(weather: Weather) {
        weather.let { it_weather ->
            binding.apply {
                cityName.text = it_weather.city.name
                temperatureValue.text = it_weather.temperature.toString()
                feelsLikeValue.text = it_weather.feelsLike.toString()
                cityCoordinates.text = "${it_weather.city.lat}/${it_weather.city.lon}"
            }

            Snackbar.make(
                binding.mainView,
                "Данные о погоде в ${it_weather.city.name} получены от сервиса",
                Snackbar.LENGTH_SHORT
            ).show()
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
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadcastReceiver)
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