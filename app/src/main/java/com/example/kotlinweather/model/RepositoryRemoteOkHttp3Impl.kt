package com.example.kotlinweather.model

import android.os.Handler
import android.os.Looper
import com.example.kotlinweather.BuildConfig
import com.example.kotlinweather.domain.City
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.domain.YANDEX_WEATHER_API_FULL_URI
import com.example.kotlinweather.domain.YANDEX_WEATHER_API_KEY
import com.example.kotlinweather.model.yandexweatherdto.WeatherDTO
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class RepositoryRemoteOkHttp3Impl : Repository {
    private val okHttpClient = OkHttpClient()
    private val requestBuilder = Request.Builder()
    private lateinit var request: Request
    private lateinit var okHttpCall: Call



    private fun makeRemoteCall() {
        okHttpCall = okHttpClient.newCall(request)
    }

    private fun initOkHttp(city: City) {
        requestBuilder.apply {
            header(YANDEX_WEATHER_API_KEY, BuildConfig.WEATHER_API_KEY)
            url("${YANDEX_WEATHER_API_FULL_URI}lat=${city.lat}&lon=${city.lon}")
        }
        request = requestBuilder.build()
    }

    override suspend fun getDirectWeather(lat: Double, lon: Double, cityName: String): Weather? {

        val response = okHttpCall
            .execute()
            .takeIf { it.isSuccessful }
            ?.body ?: return null

        val weatherDTO: WeatherDTO =
            Gson().fromJson(
                response.toString(),
                WeatherDTO::class.java
            )

        val weatherReceived = Weather(
            city = City(cityName,lat,lon),
            temperature = weatherDTO.fact.temp,
            feelsLike = weatherDTO.fact.feels_like
        )

        return (weatherReceived)

    }
}

