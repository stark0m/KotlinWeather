package com.example.kotlinweather.view.onecityview.basefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinweather.databinding.WeatherOneCityShowDialogBinding
import com.example.kotlinweather.domain.CITY_IMAGE_URL
import com.example.kotlinweather.domain.TAG_WEATHER_TO_SHOW
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.view.weathershow.WeatherShowViewModel
import com.example.kotlinweather.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import java.io.IOException




class OneCItyWeatherViewFragment : Fragment() {
    private var _binding: WeatherOneCityShowDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherToShow: Weather
    private val viewModelWeatherShow: WeatherShowViewModel by lazy {
        ViewModelProvider(this).get(WeatherShowViewModel::class.java)
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
                binding.progress.visibility = View.GONE
                showRecievedWeather(appState.weather)
                Snackbar.make(
                    binding.mainView,
                    "Данные обновлены",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            AppState.Loading -> {
                binding.progress.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.progress.visibility = View.GONE
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
                Picasso
                    .get()
                    .load(CITY_IMAGE_URL)
                    .into(binding.cityImage)
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
            OneCItyWeatherViewFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TAG_WEATHER_TO_SHOW, weather)

                }
            }
    }
}