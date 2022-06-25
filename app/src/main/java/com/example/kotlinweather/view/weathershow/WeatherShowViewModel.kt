package com.example.kotlinweather.view.weathershow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinweather.model.Repository
import com.example.kotlinweather.viewmodel.AppState

class WeatherShowViewModel(
    private val vmLiveData: MutableLiveData<Any> = MutableLiveData()
) : ViewModel() {
    var repository:Repository?= null

    private fun isConnected() = false

    private fun getData(){

        repository = if (isConnected()){RepositoryNetworkImpl} else {RepositoryLocalImpl}


    }


}