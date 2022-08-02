package com.example.kotlinweather.model

import android.util.Log
import com.example.kotlinweather.BuildConfig
import com.example.kotlinweather.domain.City
import com.example.kotlinweather.domain.RETROFIT_REPOSITORY
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.domain.YANDEX_WEATHER_API_URI
import com.example.kotlinweather.model.retrofit.GetWeatherAPI
import com.example.kotlinweather.model.yandexweatherdto.WeatherDTO
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RepositoryRemoteRetrofitImpl : Repository {
    private val retrofitBuilder: GetWeatherAPI by lazy {
        Retrofit.Builder()
            .baseUrl(YANDEX_WEATHER_API_URI)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(GetWeatherAPI::class.java)
    }


    fun WeatherDTO.toWather(city:City) =
        Weather(
            city = city,
            temperature = fact.temp,
            feelsLike = fact.feels_like,
            icon = fact.icon,
            dateUpdated = forecast.date
        )

   override suspend fun getDirectWeather(lat: Double,
                         lon: Double,
                         cityName: String):Weather? =
        retrofitBuilder.getWeather(BuildConfig.WEATHER_API_KEY, lat, lon)
            .execute()
            .takeIf { it.isSuccessful }
            ?.body()
            ?.toWather(City(cityName, lat, lon))
            ?:throw IOException("Retrofit return noSuccess result")



}

