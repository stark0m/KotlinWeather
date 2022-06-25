package com.example.kotlinweather.model

import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.domain.getDefaultCity

class RepositoryLocalImpl:Repository {
    override fun getWeatherLIst(): List<Weather> {
       return mutableListOf()
    }

    override fun getWeather(lat: Double, lon: Double): Weather {
       return Weather(getDefaultCity())
    }
}