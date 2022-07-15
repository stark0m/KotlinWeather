package com.example.kotlinweather.model.citylist

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.kotlinweather.app.RoomApp
import com.example.kotlinweather.domain.*
import com.example.kotlinweather.model.WeatherCallBack
import com.example.kotlinweather.model.room.CityListEntity
import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.function.LongFunction

class CityListRepositoryRoomImpl : CityListRepository, CityListRepositoryCreator {
    companion object {
        private const val TAG = "@@@"
    }


    private val sharedPreferencesRoom: SharedPreferences by lazy {
        RoomApp.appContext!!.getSharedPreferences(ROOM_PREFS_FILE, Context.MODE_PRIVATE)
    }

    override fun getCityList(key: CityListEnum, weatherList: WeatherCallBack<List<Weather>>) {
        Log.i(TAG, "getCityList")
        val isAppLaunchFirstTimeInDevice = sharedPreferencesRoom.getBoolean(
            IS_FIRST_LAUNCH_APP, true
        )
        Log.i(TAG, "isAppLaunchFirstTimeInDevice= $isAppLaunchFirstTimeInDevice")
        if (isAppLaunchFirstTimeInDevice) {
            Log.i(TAG, "launch first")
            sharedPreferencesRoom.edit().putBoolean(IS_FIRST_LAUNCH_APP, false).commit()
            putDefaultCitiesList()
        }

//            val cityList =
//                RoomApp.getCityListDatabase().cityListDao().getCityList(key.toString())

        var cityList: List<CityListEntity> = listOf()
        RoomApp.getCityList(key) {
            val convertedList: List<Weather> = convertFromEntityToWeatherList(it)
            weatherList.onDataReceived(convertedList)
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun updateWether(weather: Weather?) {
        if (weather != null) {
            Thread {
                with(weather) {
                    RoomApp.getCityListDatabase().cityListDao()
                        .updateWeater(city.lat, city.lon, city.name, temperature, feelsLike,dateUpdated)
                }

            }.start()


        }
    }

    private fun convertFromEntityToWeatherList(entityCityList: List<CityListEntity>): List<Weather> {
        return entityCityList.map {
            Weather(
                city = City(it.cityName, lat = it.lat, lon = it.lon),
                temperature = it.temperature,
                feelsLike = it.feelsLike
            )
        }
    }

    override fun putDefaultCitiesList() {
        var temploraryCityList: List<Weather>? = null

        CityListRepositoryHardLocalImpl().getCityList(cityListEnum = CityListEnum.RUSSIAN) {
            temploraryCityList = it
        }
        putCityListToDB(
            converterFromWeatherToEntityList(
                temploraryCityList!!,
                CityListEnum.RUSSIAN
            )
        )

        CityListRepositoryHardLocalImpl().getCityList(cityListEnum = CityListEnum.WORLD) {
            temploraryCityList = it
        }
        putCityListToDB(
            converterFromWeatherToEntityList(
                temploraryCityList!!,
                CityListEnum.WORLD
            )
        )
    }

    private fun converterFromWeatherToEntityList(
        cityList: List<Weather>,
        enum: CityListEnum
    ): List<CityListEntity> {
        return cityList.map {
            CityListEntity(
                cityName = it.city.name,
                lat = it.city.lat,
                lon = it.city.lon,
                temperature = it.temperature,
                feelsLike = it.feelsLike,
                columnCityListName = enum.toString(),
                id = 0,
                updated = it.dateUpdated
            )
        }
    }

    private fun putCityListToDB(cityList: List<CityListEntity>) {

        Thread {
            cityList.forEach {
                RoomApp.getCityListDatabase().cityListDao().insert(it)
            }
        }.start()


    }
}