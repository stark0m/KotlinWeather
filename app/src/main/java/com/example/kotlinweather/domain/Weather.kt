package com.example.kotlinweather.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0,
    val icon: String? = "bkn_n",
    val dateUpdated: String = DEFAULT_DATE

) : Parcelable

fun getDefaultCity() = City("Moscow", 55.755826, 37.617299900000035)

@Parcelize
data class City(
    val name: String,
    val lat: Double,
    val lon: Double
) : Parcelable

