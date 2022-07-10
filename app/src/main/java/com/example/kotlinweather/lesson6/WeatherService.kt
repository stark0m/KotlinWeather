package com.example.kotlinweather.lesson6

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kotlinweather.BuildConfig
import com.example.kotlinweather.domain.*
import com.example.kotlinweather.model.yandexweatherdto.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class WeatherService(name: String = "WeatherService") : IntentService(name) {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        intent?.let {

            val weather: Weather? = it.getParcelableExtra(WEATHET_DTO_KEY)

            weather?.let { weather ->
                val lat = weather.city.lat
                val lon = weather.city.lon
                val cityName = weather.city.name
                try {
                    val uri = URL("${YANDEX_WEATHER_API_URI}lat=$lat&lon=$lon")

                    lateinit var urlConnection: HttpsURLConnection
                    try {
                        urlConnection = uri.openConnection() as HttpsURLConnection
                        urlConnection.requestMethod = "GET"
                        urlConnection.addRequestProperty(
                            YANDEX_WEATHER_API_KEY,
                            BuildConfig.WEATHER_API_KEY
                        )
                        urlConnection.readTimeout = 5000
                        val bufferedReader =
                            BufferedReader(InputStreamReader(urlConnection.inputStream))

                        val weatherDTO: WeatherDTO =
                            Gson().fromJson(
                                getLines(bufferedReader),
                                WeatherDTO::class.java
                            )

                        val weatherReceived = Weather(
                            city = City(cityName, lat, lon),
                            temperature = weatherDTO.fact.temp,
                            feelsLike = weatherDTO.fact.feels_like
                        )


                        sendBackWeather(weatherReceived)


                    } catch (e: Exception) {
                        Log.e("@@@@", "Fail connection", e)
                        e.printStackTrace()
                        sendBackWeather(null)
                    } finally {
                        urlConnection.disconnect()
                    }

                } catch (e: MalformedURLException) {
                    Log.e("@@@@", "Fail URI", e)
                    e.printStackTrace()
                }
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun sendBackWeather(weather: Weather?) {
        val broadcastIntent = Intent(BROADCAST_INTENT_FILTER)
        broadcastIntent.putExtra(WEATHET_DTO_KEY, weather)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }
}