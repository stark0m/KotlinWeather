package com.example.kotlinweather.model

import com.example.kotlinweather.domain.Weather

interface Repository {
    fun getWeather( lat: Double, lon: Double,cityName:String,  weather: WeatherCallBack<Weather?>)

}