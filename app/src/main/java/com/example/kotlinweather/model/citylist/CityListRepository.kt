package com.example.kotlinweather.model.citylist

import com.example.kotlinweather.domain.CityListEnum
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.model.WeatherCallBack

interface CityListRepository {
    fun getCityList(key:CityListEnum, weatherList: WeatherCallBack<List<Weather>>)

}