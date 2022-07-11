package com.example.kotlinweather.view.weathershow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.model.*
import com.example.kotlinweather.viewmodel.AppState
import com.example.kotlinweather.viewmodel.ViewModelInterface

class WeatherShowViewModel(
    private val vmLiveData: MutableLiveData<AppState> = MutableLiveData<AppState>()
) : ViewModel(), ViewModelInterface {
    private var repository: Repository? = null
    private var cityListRepository: CityListRepository? = null

    private fun isConnected() = true

    fun getObserver(): MutableLiveData<AppState> {
        return vmLiveData
    }

    override fun getWeatherList() {
        chooseRepository()
        chooseCityListRepository()

        vmLiveData.value = AppState.Loading

        cityListRepository!!.getCityList() { result ->
            if (isWeatherReceived(result)) {
                vmLiveData.postValue(AppState.ReceivedCityListSuccess(result))
            } else {
                vmLiveData.postValue(AppState.Error(IllegalStateException("Ошибка загрузки списка городов с сервера")))
            }
        }
    }

    private fun chooseCityListRepository() {
        cityListRepository = if (!hasOutSideImplCityListRepository()) {
            CityListRepositoryHardLocalImpl()
        } else {
            CityListRepositoryHardLocalImpl()
        } //TODO здеь должена быть ссылка на имплементацию другого источника получения данных
    }

    private fun hasOutSideImplCityListRepository() = false

    override fun tryToShowWeather(weather: Weather) {
        vmLiveData.value = AppState.Loading
        vmLiveData.value = AppState.ShowWeater(weather)
    }

    override fun getAnotherCityList() {
        vmLiveData.value = AppState.Loading
        cityListRepository?.let {
            it.getNextCityList() {
                vmLiveData.postValue(AppState.ReceivedCityListSuccess(it))
            }
        }
            ?: vmLiveData.postValue(AppState.Error(IllegalStateException("Нет возможности загрузить данные")))
    }

    override fun updateWeatherInfo(weather: Weather) {
        chooseRepository()
        vmLiveData.value = AppState.Loading
        repository?.getWeather(
            lat = weather.city.lat,
            lon = weather.city.lon,
            cityName = weather.city.name

        ) { weatherFromRepository ->
            weatherFromRepository?.let {
                vmLiveData.postValue(AppState.UpdateWeatherInfo(it))
            }
                ?: vmLiveData.postValue(AppState.Error(NullPointerException("получен Null от сервера - вероятно нет доступа в интернет")))

        }
    }


    private fun isWeatherReceived(any: Any): Boolean = (0..5).random() != 1

    private fun chooseRepository() {
        repository = if (isConnected()) {
            /**
             * тут выбираем какой тип репозитория будем использовать для получения данных
             */
//            RepositoryNetworkImpl()
            RepositoryRemoteOkHttp3Impl()
        } else {
            RepositoryLocalImpl()
        }
    }


}