package com.example.kotlinweather.model

import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.domain.getDefaultCity
import java.lang.Thread.sleep

class RepositoryNetworkImpl:Repository {


    override fun getWeather(lat: Double, lon: Double, weather: WeatherCallBack<Weather?>) {
        weather.onDataReceived(Weather(getDefaultCity()))
    }

}