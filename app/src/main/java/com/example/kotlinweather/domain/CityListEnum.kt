package com.example.kotlinweather.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class CityListEnum:Parcelable {
    RUSSIAN,WORLD;
    fun getNext() =
        when(this){
            RUSSIAN ->WORLD
            WORLD -> RUSSIAN
            else -> RUSSIAN
        }

    companion object{
        fun getCityListEnumFromString(string:String) =
            when (string) {
                RUSSIAN.toString() -> RUSSIAN
                WORLD.toString() -> WORLD
                else -> RUSSIAN
            }
    }
}