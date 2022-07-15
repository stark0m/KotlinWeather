package com.example.kotlinweather.model.citylist

import android.os.Handler
import android.os.Looper
import com.example.kotlinweather.app.RoomApp
import com.example.kotlinweather.domain.City
import com.example.kotlinweather.domain.CityListEnum
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.model.WeatherCallBack
import com.example.kotlinweather.model.room.CityListEntity

class CityListRepositoryRoomImpl : CityListRepository,CityListRepositoryCreator {
    override fun getCityList(key: CityListEnum, weatherList: WeatherCallBack<List<Weather>>) {
        Thread() {
            val cityList = RoomApp.getCityListDatabase().cityListDao().getCityList(key.toString())
            val convertedList: List<Weather> = convertFromEntityToWeatherList(cityList)

            Handler(Looper.getMainLooper()).post(){
                weatherList.onDataReceived(convertedList)
            }
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
        var temploraryCityList:List<Weather>?=null

        CityListRepositoryHardLocalImpl().getCityList(cityListEnum = CityListEnum.RUSSIAN){
            temploraryCityList = it
        }
        putCityListToDB(converterFromWeatherToEntityList(temploraryCityList!!,CityListEnum.RUSSIAN))

        CityListRepositoryHardLocalImpl().getCityList(cityListEnum = CityListEnum.WORLD){
            temploraryCityList = it
        }
        putCityListToDB(converterFromWeatherToEntityList(temploraryCityList!!,CityListEnum.WORLD))
    }

    private fun converterFromWeatherToEntityList(cityList: List<Weather>, enum: CityListEnum): List<CityListEntity> {
        return cityList.map {
            CityListEntity(cityName = it.city.name,
            lat = it.city.lat,
            lon = it.city.lon,
            temperature = it.temperature,
            feelsLike = it.feelsLike,
            columnCityListName = enum.toString(),
            id =(0..1000).random().toLong() )//FIXME тут что то не так, не должны мы указывать айди
        }
    }

    private fun putCityListToDB(cityList:List<CityListEntity>) {
    Thread(){
        cityList.forEach{
            RoomApp.getCityListDatabase().cityListDao().insert(it)
        }

    }.start()
    }
}