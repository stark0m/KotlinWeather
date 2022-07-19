package com.example.kotlinweather.view.weathershow

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.kotlinweather.domain.CITY_LIST_KEY_FILE_NAME
import com.example.kotlinweather.domain.CURRENT_CITY_LIST_ENUM_NAME
import com.example.kotlinweather.domain.CityListEnum
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.model.Repository
import com.example.kotlinweather.model.RepositoryLocalImpl
import com.example.kotlinweather.model.RepositoryRemoteRetrofitImpl
import com.example.kotlinweather.model.citylist.CityListRepository
import com.example.kotlinweather.model.citylist.CityListRepositoryHardLocalImpl
import com.example.kotlinweather.model.citylist.CityListRepositoryRoomImpl
import com.example.kotlinweather.viewmodel.AppState
import com.example.kotlinweather.viewmodel.ViewModelInterface

class WeatherShowViewModel(
    application: Application,
) : AndroidViewModel(application), ViewModelInterface {
    private var repository: Repository? = null
    private lateinit var cityListRepository: CityListRepository
    private lateinit var cityListTabNameEnum: CityListEnum
    private val context = getApplication<Application>().applicationContext
    private val vmLiveData: MutableLiveData<AppState> = MutableLiveData<AppState>()
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(CITY_LIST_KEY_FILE_NAME, Context.MODE_PRIVATE)
    }
    private var cityListStringTabName: String?
        get() = sharedPreferences.getString(
            CURRENT_CITY_LIST_ENUM_NAME,
            CityListEnum.RUSSIAN.toString()
        )
        set(value) {
            sharedPreferences.edit().putString(
                CURRENT_CITY_LIST_ENUM_NAME, value
            ).apply()
        }

    private fun isConnected() = true

    fun getObserver(): MutableLiveData<AppState> {
        return vmLiveData
    }

    override fun getWeatherList() {
        cityListTabNameEnum = CityListEnum.getCityListEnumFromString(cityListStringTabName!!)
        chooseRepository()
        chooseCityListRepository()

        vmLiveData.value = AppState.Loading

        if (cityListRepository == null) {
            vmLiveData.postValue(AppState.Error(IllegalStateException("ошибка загрузки списка городов")))
        } else {
            tryToreceiveCityList()
        }


    }

    private fun tryToreceiveCityList() {
        cityListRepository!!.getCityList(cityListTabNameEnum) { result ->
            vmLiveData.postValue(AppState.ReceivedCityListSuccess(result))
        }
    }


    private fun chooseCityListRepository() {
        /**
         * выбираем источник загрузки списков городов
         */
        cityListRepository = when (2) {
            1 -> CityListRepositoryHardLocalImpl()
            else -> CityListRepositoryRoomImpl()
        }
    }


    override fun tryToShowWeather(weather: Weather) {
        vmLiveData.value = AppState.Loading
        vmLiveData.value = AppState.ShowWeater(weather)

    }

    override fun getAnotherCityList() {
        vmLiveData.value = AppState.Loading
        cityListTabNameEnum = cityListTabNameEnum.getNext()
        cityListStringTabName = cityListTabNameEnum.toString()

        getWeatherList()

    }

    override fun updateWeatherInfo(weather: Weather) {
        chooseRepository()
        chooseCityListRepository()
        vmLiveData.value = AppState.Loading
        repository?.getWeather(
            lat = weather.city.lat,
            lon = weather.city.lon,
            cityName = weather.city.name

        ) { weatherFromRepository ->

            cityListRepository.updateWether(weatherFromRepository)

            weatherFromRepository?.let {
                vmLiveData.postValue(AppState.UpdateWeatherInfo(it))


            }
                ?: vmLiveData.postValue(AppState.Error(NullPointerException("получен Null от сервера - вероятно нет доступа в интернет")))

        }
    }

    override fun tryToShowGeocoder() {
        vmLiveData.postValue(AppState.ShowGeocoder)
    }

    override fun addCityToCurrentList(weather: Weather) {
        cityListRepository.addLocation(weather,cityListTabNameEnum){result->
            if (result) {
                tryToreceiveCityList()
            }
        }
    }


    private fun chooseRepository() {
        repository = if (isConnected()) {
            /**
             * тут выбираем какой тип репозитория будем использовать для получения данных
             */
//            RepositoryNetworkImpl()
//            RepositoryRemoteOkHttp3Impl()
            RepositoryRemoteRetrofitImpl()
        } else {
            RepositoryLocalImpl()
        }
    }


}