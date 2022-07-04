package com.example.kotlinweather.model

import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.kotlinweather.BuildConfig.WEATHER_API_KEY
import com.example.kotlinweather.domain.City
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.domain.dto.yandexweatherdto.WeatherData
import com.example.kotlinweather.domain.getDefaultCity
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class RepositoryNetworkImpl : Repository {
    companion object {
        private val URI = "https://api.weather.yandex.ru/v2/informers?"
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getWeather(
        lat: Double,
        lon: Double,
        cityName: String,
        weather: WeatherCallBack<Weather?>
    ) {
        try {
            val uri =
                URL("${URI}lat=$lat&lon=$lon")
            val handler = Handler()
            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.addRequestProperty(
                        "X-Yandex-API-Key",
                        WEATHER_API_KEY
                    )
                    urlConnection.readTimeout = 10000
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))
// преобразование ответа от сервера (JSON) в модель данных

                    val weatherDTO: WeatherData =
                        Gson().fromJson(
                            getLines(bufferedReader),
                            WeatherData::class.java
                        )

                    val weatherReceived = Weather(
                        city = City(cityName, lat, lon),
                        temperature = weatherDTO.fact.temp,
                        feelsLike = weatherDTO.fact.feels_like
                    )

                    handler.post { weather.onDataReceived(weatherReceived) }

                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
//Обработка ошибки
                } finally {
                    urlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
//Обработка ошибки
        }



        weather.onDataReceived(Weather(getDefaultCity()))
    }

}