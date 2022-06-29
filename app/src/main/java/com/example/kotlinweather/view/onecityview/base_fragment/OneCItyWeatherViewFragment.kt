package com.example.kotlinweather.view.onecityview.base_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinweather.databinding.FragmentOneCItyWeatherViewBinding
import com.example.kotlinweather.domain.Weather

private var _binding: FragmentOneCItyWeatherViewBinding? = null
private val binding get() = _binding!!

private const val TAG_WEATHER_TO_SHOW = "TAG_WEATHER_TO_SHOW"


class OneCItyWeatherViewFragment : Fragment() {

    private var weatherToShow: Weather? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            weatherToShow = it.getParcelable<Weather>(TAG_WEATHER_TO_SHOW)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       showRecievedWeather()

    }

    private fun showRecievedWeather() {
        weatherToShow?.let {weather->
            binding.apply{
                cityName.text = weather.city.name
                temperatureValue.text = weather.temperature.toString()
                feelsLikeValue.text = weather.feelsLike.toString()
                cityCoordinates.text = "${weather.city.lat}/${weather.city.lon}"
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