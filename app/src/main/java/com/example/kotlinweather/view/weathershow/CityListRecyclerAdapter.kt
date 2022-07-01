package com.example.kotlinweather.view.weathershow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinweather.databinding.CityItemBinding
import com.example.kotlinweather.domain.Weather

class CityListRecyclerAdapter(private val dataList:List<Weather>, private val callback: ChooseCity):RecyclerView.Adapter<CityListRecyclerAdapter.WeatherViewHolder>() {
    inner class WeatherViewHolder(view: View):RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = CityItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WeatherViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {

        val binding = CityItemBinding.bind(holder.itemView)
        dataList[position].apply {
            city.also { binding.idCityName.text = it.name }

        binding.root.setOnClickListener(){
            callback.onCityClicked(this)
        }
        }
    }

    override fun getItemCount() = dataList.size

}

