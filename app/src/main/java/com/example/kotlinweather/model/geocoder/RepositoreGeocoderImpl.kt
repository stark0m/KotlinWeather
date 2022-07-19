package com.example.kotlinweather.model.geocoder

import android.annotation.SuppressLint
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
import com.example.kotlinweather.domain.GPS_ACCESS_PERMISSION
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.model.AppCallback


private const val DEFAULT_CITY_NAME = "unknown"

class RepositoreGeocoderImpl : RepositoryGeocoder {

    private val context = RoomApp.appContext
    private val geoCoder: Geocoder by lazy { Geocoder(context) }
    private val locationManager by lazy { context?.getSystemService(LOCATION_SERVICE) as LocationManager }


    override fun getLocationList(address: String, callback: AppCallback<List<Weather>>) {
        Thread {

            val addresses = geoCoder.getFromLocationName(address, 5)

            if (addresses.size > 0) {
                val resultWeatherList:List<Weather> = convertAddressListToWeatherList(addresses)

                Handler(Looper.getMainLooper()).post {
                    callback.onClick(resultWeatherList)
                }

            }
        }.start()
    }

    private fun convertAddressListToWeatherList(addresses: List<Address>): List<Weather> {
        return addresses.map {
            Weather(City(it.locality ?: DEFAULT_CITY_NAME, it.latitude, it.longitude))
        }

    }

    @SuppressLint("MissingPermission")
    override fun getGPSLocation(callback: AppCallback<Location?>) {

        if (ActivityCompat.checkSelfPermission(
                context!!,
                GPS_ACCESS_PERMISSION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@getGPSLocation
        }

        Thread {
            val phoneCoordinates =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            Handler(Looper.getMainLooper()).post {
                callback.onClick(phoneCoordinates)
            }
        }.start()
    }

    override fun getLocationByCoordinates(lat: Double, lon: Double): String {
        return geoCoder.getFromLocation(lat, lon, 1).getOrNull(0)?.locality ?: DEFAULT_CITY_NAME
    }
}