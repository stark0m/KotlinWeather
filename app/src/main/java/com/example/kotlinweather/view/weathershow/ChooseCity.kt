package com.example.kotlinweather.view.weathershow

import com.example.kotlinweather.domain.Weather

fun interface ChooseCity {
    fun onCityClicked(weather: Weather)
}