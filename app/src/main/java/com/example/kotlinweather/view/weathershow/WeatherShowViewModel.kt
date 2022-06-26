package com.example.kotlinweather.view.weathershow

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.domain.getDefaultCity
import com.example.kotlinweather.model.Repository
import com.example.kotlinweather.model.RepositoryLocalImpl
import com.example.kotlinweather.model.RepositoryNetworkImpl
import com.example.kotlinweather.model.WeatherCallBack
import com.example.kotlinweather.viewmodel.AppState
import com.example.kotlinweather.viewmodel.ViewModelInterface
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class WeatherShowViewModel(
    private val vmLiveData: MutableLiveData<AppState> = MutableLiveData<AppState>()
) : ViewModel(), ViewModelInterface {
    var repository: Repository? = null

    private fun isConnected() = false

    fun getObserver():MutableLiveData<AppState>{
        return vmLiveData
    }

    override fun getData() {
        chooseRepository()

        vmLiveData.value = AppState.Loading

        repository!!.getWeatherLIst { result ->
            if (isWeatherReceived(result)) {
                vmLiveData.postValue(AppState.ReceivedCityListSuccess(result))
                Log.i("@@@@", result.toString())
            } else {
                vmLiveData.postValue(AppState.Error(Any()))

            }
        }
//        repository!!.getWeather(55.755826, 37.617299900000035, object : WeatherCallBack<Weather> {
//            override fun onDataReceived(result: Weather) {
//                if (isWeatherReceived(result)) {
//                    vmLiveData.postValue(AppState.Success(result))
//
//                } else {
//                    vmLiveData.postValue(AppState.Error(Any()))
//
//                }
//            }
//
//        })




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