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

   suspend fun getDirectWeather(lat: Double,
                         lon: Double,
                         cityName: String):Weather? =
        retrofitBuilder.getWeather(BuildConfig.WEATHER_API_KEY, lat, lon)
            .execute()
            .takeIf { it.isSuccessful }
            ?.body()
            ?.toWather(City(cityName, lat, lon))
            ?:throw IOException("Retrofit return noSuccess result")


    override fun getWeather(
        lat: Double,
        lon: Double,
        cityName: String,
        weather: WeatherCallBack<Weather?>
    ) {

        /*  retrofitBuilder.getWeather(BuildConfig.WEATHER_API_KEY, lat, lon)
              .enqueue(object : retrofit2.Callback<WeatherDTO> {
                  override fun onFailure(call: retrofit2.Call<WeatherDTO>, t: Throwable) {
                      sendBackError(t.message.toString(), weather)
                  }

                  override fun onResponse(
                      call: retrofit2.Call<WeatherDTO>,
                      serverResponce: retrofit2.Response<WeatherDTO>
                  ) {
                      val responce = serverResponce.body()
                      if (!serverResponce.isSuccessful){
                          sendBackError("server error code=${serverResponce.code()}", weather)
                      }
                      if (responce != null) {
                          val weatherReceived = Weather(
                              city = City(cityName, lat, lon),
                              temperature = responce.fact.temp,
                              feelsLike = responce.fact.feels_like,
                              icon = responce.fact.icon,
                              dateUpdated = responce.forecast.date
                          )
                          weather.onDataReceived(weatherReceived)
                      } else {
                          sendBackError("serverResponce.body()=null", weather)
                      }
                  }
              })*/

        Thread {
            val result = retrofitBuilder.getWeather(BuildConfig.WEATHER_API_KEY, lat, lon)
                .execute()
                .takeIf { it.isSuccessful }
                ?.body()
                ?.toWather(City(cityName, lat, lon))


            if (result!=null){
                weather.onDataReceived(result)
            }


        }.start()


    }


//            ?.let{val weatherReceived = Weather(
//                        city = City(cityName, lat, lon),
//                        temperature = it.fact.temp,
//                        feelsLike = it.fact.feels_like,
//                        icon = it.fact.icon,
//                        dateUpdated = it.forecast.date
//                    )
//                        weather.onDataReceived(weatherReceived)
//
//                    }




private fun sendBackError(message: String, weather: WeatherCallBack<Weather?>) {
    Log.e(RETROFIT_REPOSITORY, message)
    weather.onDataReceived(null)

}

}

