package com.example.kotlinweather.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityListDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityListEntity: CityListEntity)

    @Query("SELECT * FROM city_list_entity_table WHERE columnCityListName=:cityListName")
    fun getCityList(cityListName:String):List<CityListEntity>


}