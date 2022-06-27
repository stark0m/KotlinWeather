package com.example.kotlinweather.model

import com.example.kotlinweather.domain.Weather

interface CityListRepository {
    fun getCityList(weatherList: WeatherCallBack<List<Weather>>)
    fun getNextCityList(weatherList: WeatherCallBack<List<Weather>>)
}