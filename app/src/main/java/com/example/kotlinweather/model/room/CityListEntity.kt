package com.example.kotlinweather.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "city_list_entity_table")
data class CityListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val cityName: String,
    val lat: Double,
    val lon: Double,
    val temperature: Int = 0,
    val feelsLike: Int = 0,
    val columnCityListName: String
)
