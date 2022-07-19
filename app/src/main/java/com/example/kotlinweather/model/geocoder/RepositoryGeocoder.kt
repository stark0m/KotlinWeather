package com.example.kotlinweather.model.geocoder

import android.location.Location
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.model.AppCallback

interface RepositoryGeocoder {
    fun getLocationList(address:String,callback:AppCallback<List<Weather>>)
    fun getGPSLocation(callback: AppCallback<Location?>)
}