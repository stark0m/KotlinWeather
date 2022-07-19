package com.example.kotlinweather.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinweather.R
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