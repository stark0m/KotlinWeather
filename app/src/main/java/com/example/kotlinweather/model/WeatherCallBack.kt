package com.example.kotlinweather.model

fun interface WeatherCallBack<T> {
    fun onDataReceived(result: T)
}