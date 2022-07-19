package com.example.kotlinweather.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CityListEntity::class), version = 1)
abstract class CityListDatabase:RoomDatabase() {
    abstract fun cityListDao():CityListDAO
}