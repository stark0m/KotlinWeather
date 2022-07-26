package com.example.kotlinweather.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class CityListEnum:Parcelable {
    RUSSIAN,WORLD,CUSTOM;
    fun getNext() =
        when(this){
            RUSSIAN ->WORLD
            WORLD -> CUSTOM
            CUSTOM -> RUSSIAN
            else -> CUSTOM
        }

    companion object{
        fun getCityListEnumFromString(string:String) =
            when (string) {
                RUSSIAN.toString() -> RUSSIAN
                WORLD.toString() -> WORLD
                CUSTOM.toString() -> CUSTOM
                else -> RUSSIAN
            }
    }
}