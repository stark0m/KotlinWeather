package com.example.kotlinweather.model

import android.util.Log
import com.example.kotlinweather.BuildConfig
import com.example.kotlinweather.domain.City
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.domain.YANDEX_WEATHER_API_URI
import com.example.kotlinweather.model.retrofit.GetWeatherAPI
import com.example.kotlinweather.model.yandexweatherdto.WeatherDTO
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryRemoteRetrofitImpl : Repository {
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(YANDEX_WEATHER_API_URI)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(GetWeatherAPI::class.java)


    override fun getWeather(
        lat: Double,
        lon: Double,
        cityName: String,
        weather: WeatherCallBack<Weather?>
    ) {

        retrofitBuilder.getWeather(BuildConfig.WEATHER_API_KEY, lat, lon)
            .enqueue(object : retrofit2.Callback<WeatherDTO> {
                override fun onFailure(call: retrofit2.Call<WeatherDTO>, t: Throwable) {
                    sendBackError(t, weather)
                }

                override fun onResponse(
                    call: retrofit2.Call<WeatherDTO>,
                    serverResponce: retrofit2.Response<WeatherDTO>
                ) {

                    try {
                        val responce = serverResponce.body()

                        val weatherReceived = Weather(
                            city = City(cityName, lat, lon),
                            temperature = responce!!.fact.temp,
                            feelsLike = responce!!.fact.feels_like
                        )
                        weather.onDataReceived(weatherReceived)
                    } catch (e: Exception) {
                        sendBackError(e, weather)
                    }
                }
            })
    }


    private fun sendBackError(e: Throwable, weather: WeatherCallBack<Weather?>) {
        Log.e("RETROFIT DATA",e.message.toString())
        weather.onDataReceived(null)

    }

}

