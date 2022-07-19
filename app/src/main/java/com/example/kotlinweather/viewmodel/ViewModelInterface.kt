package com.example.kotlinweather.viewmodel

import com.example.kotlinweather.domain.Weather

interface ViewModelInterface {
    fun getWeatherList()
    fun tryToShowWeather(weather: Weather)
    fun getAnotherCityList()
    fun updateWeatherInfo(weather: Weather)
    fun tryToShowGeocoder()
    fun addCityToCurrentList(weather: Weather)
}