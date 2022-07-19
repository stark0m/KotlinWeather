package com.example.kotlinweather.model.geocoder

import android.location.Address
import android.location.Geocoder
import android.os.Handler
import android.os.Looper
import com.example.kotlinweather.app.RoomApp
import com.example.kotlinweather.domain.City
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.model.AppCallback

class RepositoreGeocoderImpl:RepositoryGeocoder {
    private val context = RoomApp.appContext
    private val geoCoder :Geocoder by lazy {Geocoder(context)}
    override fun getLocationList(address: String, callback: AppCallback<List<Weather>>) {


        Thread{
            val addresses = geoCoder.getFromLocationName(address, 5)
            if (addresses.size > 0) {
                val resultWeatherList = convertAddressListToWeatherList(addresses)
                Handler(Looper.getMainLooper()).post{
                    callback.onClick(resultWeatherList)
                }
            }
        }.start()
    }

    private fun convertAddressListToWeatherList(addresses: List<Address>): List<Weather> {

        return addresses.map { Weather(City(it.featureName,it.latitude,it.latitude)) }

    }
}