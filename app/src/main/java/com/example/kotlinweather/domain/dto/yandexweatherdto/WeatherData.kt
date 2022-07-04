package com.example.kotlinweather.domain.dto.yandexweatherdto

data class WeatherData(
    val fact: Fact,
    val forecast: Forecast,
    val info: Info,
    val now: Int,
    val now_dt: String
)