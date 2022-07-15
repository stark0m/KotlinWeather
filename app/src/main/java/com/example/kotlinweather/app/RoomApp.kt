package com.example.kotlinweather.app

import android.app.Application
import androidx.room.Room
import com.example.kotlinweather.domain.ROOM_DB_NAME
import com.example.kotlinweather.model.room.CityListDatabase

class RoomApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this

    }

    companion object {
        private var cityListDatabase: CityListDatabase? = null
        private var appContext: RoomApp? = null
        private fun getContext() = appContext
        fun getCityListDatabase():CityListDatabase {
            if (cityListDatabase == null) {
                cityListDatabase = Room.databaseBuilder(
                    getContext()!!,
                    CityListDatabase::class.java,
                    ROOM_DB_NAME
                ).build()
            }
            return cityListDatabase!!
        }
    }

}