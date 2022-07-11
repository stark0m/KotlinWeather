package com.example.kotlinweather.model

import android.os.Handler
import android.os.Looper
import com.example.kotlinweather.BuildConfig
import com.example.kotlinweather.domain.*
import com.example.kotlinweather.model.yandexweatherdto.WeatherDTO
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class RepositoryRemoteRetrofitImpl:Repository {
    private val okHttpClient = OkHttpClient()
    private val requestBuilder = Request.Builder()
    private lateinit var request: Request
    private lateinit var okHttpCall:Call
    override fun getWeather(
        lat: Double,
        lon: Double,
        cityName: String,
        weather: WeatherCallBack<Weather?>
    ) {
        initOkHttp(City(cityName,lat,lon))
        makeRemoteCall()
        setOkHttp3CallbackListener(City(cityName,lat,lon),weather)
    }

    private fun setOkHttp3CallbackListener(cityReceived: City, weather: WeatherCallBack<Weather?>) {
        okHttpCall.enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                sendBackError(UnsupportedOperationException(),weather)
            }

            override fun onResponse(call: Call, serverResponse: Response) {
                val response:String? = serverResponse.body?.string()
                if (serverResponse.isSuccessful && response!=null){


                    try {
                        val weatherDTO: WeatherDTO =
                            Gson().fromJson(
                                response,
                                WeatherDTO::class.java
                            )



                        val weatherReceived = Weather(
                            city = cityReceived,
                            temperature = weatherDTO.fact.temp,
                            feelsLike = weatherDTO.fact.feels_like
                        )

                        Handler(Looper.getMainLooper()).post(){
                            weather.onDataReceived(weatherReceived)
                        }
                    } catch (e:Exception){
                        sendBackError(e,weather)
                    }



                } else
                {
                    sendBackError(IOException(),weather)
                }
            }

        }

        )
    }




    private fun sendBackError(e:Exception, weather: WeatherCallBack<Weather?>){
        weather.onDataReceived(null)

    }
    private fun makeRemoteCall() {
        okHttpCall = okHttpClient.newCall(request)
    }

    private fun initOkHttp(city: City) {
        requestBuilder.apply {
            header(YANDEX_WEATHER_API_KEY, BuildConfig.WEATHER_API_KEY)
            url("${YANDEX_WEATHER_API_URI}lat=${city.lat}&lon=${city.lon}")
        }
        request = requestBuilder.build()
    }
}

