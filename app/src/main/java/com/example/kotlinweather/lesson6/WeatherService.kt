package com.example.kotlinweather.lesson6

import android.app.IntentService
import android.content.Intent
import com.example.kotlinweather.domain.BROADCAST_INTENT_FILTER
import com.example.kotlinweather.domain.WEATHET_DTO_KEY
import com.example.kotlinweather.domain.Weather

class WeatherService(name:String = "WeatherService"):IntentService(name) {
    override fun onHandleIntent(p0: Intent?) {
        TODO("Not yet implemented")
    }

    fun sendBackWeather(weather: Weather){
        val broadcastIntent = Intent(BROADCAST_INTENT_FILTER)
        broadcastIntent.putExtra(WEATHET_DTO_KEY,weather)
        sendBroadcast(broadcastIntent)
    }
}