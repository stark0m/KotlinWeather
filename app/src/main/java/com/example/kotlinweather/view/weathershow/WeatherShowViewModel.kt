package com.example.kotlinweather.view.weathershow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinweather.model.Repository
import com.example.kotlinweather.model.RepositoryLocalImpl
import com.example.kotlinweather.model.RepositoryNetworkImpl
import com.example.kotlinweather.viewmodel.AppState
import com.example.kotlinweather.viewmodel.ViewModelInterface

class WeatherShowViewModel(
    private val vmLiveData: MutableLiveData<Any> = MutableLiveData()
) : ViewModel(),ViewModelInterface{
    var repository: Repository? = null

    private fun isConnected() = false

    override fun getData() {
        chooseRepository()
        vmLiveData.value = AppState.Loading


    }

    private fun chooseRepository() {
        repository = if (isConnected()) {
            RepositoryNetworkImpl()
        } else {
            RepositoryLocalImpl()
        }
    }


}