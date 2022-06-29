package com.example.kotlinweather.view.weathershow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinweather.R
import com.example.kotlinweather.domain.Weather

class CityListRecyclerAdapter(private val dataList:List<Weather>, private val callback: ChooseCity):RecyclerView.Adapter<CityListRecyclerAdapter.WeatherViewHolder>() {
    inner class WeatherViewHolder(view: View):RecyclerView.ViewHolder(view){
        val cityName: TextView
        init {
            cityName = view.findViewById(R.id.id_city_name)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        dataList[position].apply {
            city.also { holder.cityName.text = it.name }

        holder.itemView.setOnClickListener(){
            callback.onCityClicked(this)
        }
        }
    }

    override fun getItemCount(): Int {
       return dataList.size
    }
}

