package com.example.kotlinweather.model

import com.example.kotlinweather.domain.Weather

interface Repository {
    fun getWeatherLIst(weatherList: WeatherCallBack<List<Weather>>)
    fun getWeather( lat: Double, lon: Double,  weather: WeatherCallBack<Weather>)

}