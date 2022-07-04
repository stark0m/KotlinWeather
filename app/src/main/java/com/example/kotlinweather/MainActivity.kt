package com.example.kotlinweather

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinweather.BuildConfig.WEATHER_API_KEY
import com.example.kotlinweather.view.weathershow.WeatherShowFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherShowFragment.newInstance()).commit()
        }
    }


}