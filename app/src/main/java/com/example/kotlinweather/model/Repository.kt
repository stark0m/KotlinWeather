package com.example.kotlinweather.model

import com.example.kotlinweather.domain.Weather

interface Repository {
    fun getWeatherLIst():List<Weather>
    fun getWeather( lat: Double, lon: Double): Weather

}