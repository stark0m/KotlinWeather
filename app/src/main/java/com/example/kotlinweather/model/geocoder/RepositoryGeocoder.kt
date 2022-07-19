package com.example.kotlinweather.model.geocoder

import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.model.AppCallback

interface RepositoryGeocoder {
    fun getLocationList(address:String,callback:AppCallback<List<Weather>>)
}