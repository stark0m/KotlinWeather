package com.example.kotlinweather.model

import android.os.Handler
import android.os.Looper
import com.example.kotlinweather.domain.Weather
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class RepositoryLocalImpl : Repository {


    //    override fun getWeather(
//        lat: Double,
//        lon: Double,
//        cityName: String,
//        weather: WeatherCallBack<Weather?>
//    ) {
//        val handler = Handler(Looper.getMainLooper())
//        thread {
//            sleep(2000L)
//            handler.post(){
//                weather.onDataReceived(null)
//            }
//
//        }.start()
//    }
    override suspend fun getDirectWeather(lat: Double, lon: Double, cityName: String): Weather? = null


}
