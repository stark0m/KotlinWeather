package com.example.kotlinweather.view.weathershow

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinweather.domain.Weather

class CityListRecyclerAdapter(private val dataList:List<Weather>, private val callback: ChooseCity):RecyclerView.Adapter<CityListRecyclerAdapter.WeatherViewHolder>() {
    inner class WeatherViewHolder(view: View):RecyclerView.ViewHolder(view){
// TODO:
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}

