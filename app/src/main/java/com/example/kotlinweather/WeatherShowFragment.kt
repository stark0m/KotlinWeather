package com.example.kotlinweather

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class WeatherShowFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherShowFragment()
    }

    private lateinit var viewModelWeatherShow: WeatherShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_show_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelWeatherShow = ViewModelProvider(this).get(WeatherShowViewModel::class.java)

    }

}