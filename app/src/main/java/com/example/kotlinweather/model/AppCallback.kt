package com.example.kotlinweather.model

fun interface AppCallback<T> {
    fun onClick(result: T)
}