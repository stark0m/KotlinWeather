package com.example.kotlinweather.model.geocoder

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.example.kotlinweather.app.RoomApp
import com.example.kotlinweather.domain.City
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.model.AppCallback


class RepositoreGeocoderImpl : RepositoryGeocoder {
    private val context = RoomApp.appContext
    private val geoCoder: Geocoder by lazy { Geocoder(context) }
    private val locationManager by lazy { context?.getSystemService(LOCATION_SERVICE) as LocationManager }
    override fun getLocationList(address: String, callback: AppCallback<List<Weather>>) {


        Thread {
            val addresses = geoCoder.getFromLocationName(address, 5)
            if (addresses.size > 0) {
                val resultWeatherList = convertAddressListToWeatherList(addresses)
                Handler(Looper.getMainLooper()).post {
                    callback.onClick(resultWeatherList)
                }
            }
        }.start()
    }

    private fun convertAddressListToWeatherList(addresses: List<Address>): List<Weather> {

        return addresses.map { Weather(City(it.featureName, it.latitude, it.latitude)) }

    }

    override fun getGPSLocation(callback: AppCallback<Location?>) {

        if (ActivityCompat.checkSelfPermission(context!!,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            return
        }

        Thread {
            val coord = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            Handler(Looper.getMainLooper()).post {
                callback.onClick(coord)
            }

        }.start()


    }
}