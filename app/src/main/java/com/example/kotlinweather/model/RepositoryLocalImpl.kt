package com.example.kotlinweather.model

import com.example.kotlinweather.domain.Weather
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class RepositoryLocalImpl : Repository {


    override fun getWeather(
        lat: Double,
        lon: Double,
        cityName: String,
        weather: WeatherCallBack<Weather?>
    ) {
        thread {
            sleep(2000L)
            weather.onDataReceived(null)
        }.start()
    }


}
