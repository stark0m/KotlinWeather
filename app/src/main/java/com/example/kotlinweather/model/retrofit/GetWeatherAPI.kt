package com.example.kotlinweather.model.retrofit

import com.example.kotlinweather.domain.LAT
import com.example.kotlinweather.domain.LON
import com.example.kotlinweather.domain.YANDEX_WEATHER_API_KEY
import com.example.kotlinweather.model.yandexweatherdto.WeatherDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetWeatherAPI {
    @GET("v2/informers?")
    fun getWeather(
        @Header(YANDEX_WEATHER_API_KEY) token:String,
        @Query(LAT) lat:Double,
        @Query(LON) lon:Double
    ): Call<WeatherDTO>
}


