package com.example.kotlinweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.kotlinweather.databinding.WeatherShowFragmentBinding
import com.example.kotlinweather.view.weathershow.WeatherShowFragment
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.container,WeatherShowFragment.newInstance()).commit()
        }
        }


}