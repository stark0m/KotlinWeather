package com.example.kotlinweather.view.weathershow

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.domain.getDefaultCity
import com.example.kotlinweather.model.*
import com.example.kotlinweather.viewmodel.AppState
import com.example.kotlinweather.viewmodel.ViewModelInterface
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class WeatherShowViewModel(
    private val vmLiveData: MutableLiveData<AppState> = MutableLiveData<AppState>()
) : ViewModel(), ViewModelInterface {
    var repository: Repository? = null
    var cityListRepository: CityListRepository? = null

    private fun isConnected() = false

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
                vmLiveData.postValue(AppState.Error(Any()))

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
        cityListRepository!!.getNextCityList(){
            vmLiveData.postValue(AppState.ReceivedCityListSuccess(it))
        }
    }


    private fun isWeatherReceived(any: Any): Boolean = (0..5).random() != 1

    private fun chooseRepository() {
        repository = if (isConnected()) {
            RepositoryNetworkImpl()
        } else {
            RepositoryLocalImpl()
        }
    }


}