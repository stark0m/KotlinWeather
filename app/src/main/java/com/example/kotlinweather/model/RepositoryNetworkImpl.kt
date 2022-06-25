package com.example.kotlinweather.model

import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.domain.getDefaultCity
import java.lang.Thread.sleep

class RepositoryNetworkImpl:Repository {
    override fun getWeatherLIst(): List<Weather> {
        Thread{
            sleep(2000L)
        }.start()
        return mutableListOf()
    }

    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread{
            sleep(2000L)
        }.start()
        return Weather(getDefaultCity())
    }
}