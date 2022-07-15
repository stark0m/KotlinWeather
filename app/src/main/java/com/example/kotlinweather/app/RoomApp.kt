package com.example.kotlinweather.app

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import com.example.kotlinweather.domain.CityListEnum
import com.example.kotlinweather.domain.ROOM_DB_NAME
import com.example.kotlinweather.model.WeatherCallBack
import com.example.kotlinweather.model.room.CityListDatabase
import com.example.kotlinweather.model.room.CityListEntity

class RoomApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this

    }

    companion object {
        private var cityListDatabase: CityListDatabase? = null
        var appContext: RoomApp? = null
        private fun getContext() = appContext
        fun getCityListDatabase(): CityListDatabase {
            if (cityListDatabase == null) {
                cityListDatabase = Room.databaseBuilder(
                    getContext()!!,
                    CityListDatabase::class.java,
                    ROOM_DB_NAME
                ).build()
            }
            return cityListDatabase!!
        }

        fun getCityList(key:CityListEnum, weatherList: WeatherCallBack<List<CityListEntity>>){
            Thread{
                val list = getCityListDatabase().cityListDao().getCityList(key.toString())

                Handler(Looper.getMainLooper()).post{
                    weatherList.onDataReceived(list)
                }
            }.start()

        }
    }

}