package com.example.kotlinweather.domain

import android.Manifest


//FCM
const val CHANNEL_ID_HIGHT= "CHANNEL_ID_HIGHT"
const val NOTIFICATION_ID= 99

const val NOTIFICATION_KEY_TITLE = "myTitle"
const val NOTIFICATION_KEY_BODY = "myBody"

const val BROADCAST_INTENT_FILTER = "BROADCAST_INTENT_FILTER"
const val TAG_WEATHER_TO_SHOW = "TAG_WEATHER_TO_SHOW"
const val WEATHET_DTO_KEY = "WEATHET_DTO_KEY"
const val YANDEX_WEATHER_API_FULL_URI = "https://api.weather.yandex.ru/v2/informers?"
const val YANDEX_WEATHER_API_URI = "https://api.weather.yandex.ru/"
const val YANDEX_WEATHER_API_KEY = "X-Yandex-API-Key"
const val CITY_IMAGE_URL = "https://freepngimg.com/thumb/city/36275-3-city-hd.png"
const val CITY_IMAGE_WEATHER_URL = "https://yastatic.net/weather/i/icons/funky/dark/"
const val RETROFIT_REPOSITORY = "RETROFIT_REPOSITORY"
const val CITY_LIST_KEY_FILE_NAME = "CITY_LIST_KEY_FILE_NAME"
const val CURRENT_CITY_LIST_ENUM_NAME = "CITY_LIST_KEY_FILE_NAME"
const val IS_FIRST_LAUNCH_APP = "IS_FIRST_LAUNCH_APP"
const val ROOM_PREFS_FILE = "ROOM_PREFS_FILE"
const val TEMP_PLUS = "T +"
const val TEMP_MINUS = "T -"
const val DEFAULT_DATE="00"

const val GPS_ACCESS_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION

const val ROOM_DB_NAME= "CITY_LIST_DB"

const val LAT = "lat"
const val LON = "lon"
