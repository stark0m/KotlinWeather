package com.example.kotlinweather.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinweather.domain.Weather

@Dao
interface CityListDAO {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityListEntity: CityListEntity)

    @Query("SELECT * FROM city_list_entity_table WHERE columnCityListName=:cityListName")
    fun getCityList(cityListName:String):List<CityListEntity>

    @Query("UPDATE city_list_entity_table SET temperature=:temperature,feelsLike=:feelsLike,updated=:dateString WHERE lat=:lat AND lon=:lon AND cityName=:cityName")
    fun updateWeater(lat:Double,lon:Double,cityName:String,temperature:Int,feelsLike:Int,dateString:String)

}

