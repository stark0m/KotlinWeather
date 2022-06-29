package com.example.kotlinweather.view.onecityview.base_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinweather.databinding.FragmentOneCItyWeatherViewBinding
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.view.weathershow.WeatherShowViewModel
import com.example.kotlinweather.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar
import java.io.IOException


private const val TAG_WEATHER_TO_SHOW = "TAG_WEATHER_TO_SHOW"


class OneCItyWeatherViewFragment : Fragment() {
    private var _binding: FragmentOneCItyWeatherViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherToShow: Weather
    private val viewModelWeatherShow: WeatherShowViewModel by lazy {
        ViewModelProvider(this).get(WeatherShowViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            var weatherFromBundle = it.getParcelable<Weather>(TAG_WEATHER_TO_SHOW)
            weatherToShow = weatherFromBundle
                ?: throw IOException("Не получен бандл с городом для отображения погоды ")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRecievedWeather(weatherToShow)
        initObserverFromViewModel()
        sendRequestToUpdateCurrentCityWeatherInfo()
    }

    private fun sendRequestToUpdateCurrentCityWeatherInfo() {
        viewModelWeatherShow.updateWeatherInfo(weatherToShow)
    }

    private fun initObserverFromViewModel() {
        viewModelWeatherShow.getObserver()
            .observe(viewLifecycleOwner) {
                makeAction(it)
            }
    }

    private fun makeAction(appState: AppState) {

        when (appState) {

            is AppState.UpdateWeatherInfo -> {
                showRecievedWeather(appState.weather)
            }
            is AppState.Error -> {
                Snackbar
                    .make(
                        binding.mainView,
                        appState.error.message.toString(),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    .setAction("Try Again") { viewModelWeatherShow.updateWeatherInfo(weatherToShow) }
                    .show()
            }
            else -> {}
        }
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
    ): View? {
        _binding = FragmentOneCItyWeatherViewBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(weather: Weather) =
            OneCItyWeatherViewFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TAG_WEATHER_TO_SHOW, weather)

                }
            }
    }
}