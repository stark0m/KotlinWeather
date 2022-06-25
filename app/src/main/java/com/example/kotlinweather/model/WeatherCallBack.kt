package com.example.kotlinweather.model

interface WeatherCallBack<T> {
    fun onDataReceived(result:T)
}