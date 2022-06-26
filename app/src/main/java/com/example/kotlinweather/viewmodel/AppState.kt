package com.example.kotlinweather.viewmodel

import com.example.kotlinweather.domain.Weather

sealed class AppState {
    data class Success(val weatherData: Weather) : AppState()
    data class ReceivedCityListSuccess(val cityList: List<Weather>) : AppState()
    data class Error(val error: Any) : AppState()
    object Loading : AppState()
}