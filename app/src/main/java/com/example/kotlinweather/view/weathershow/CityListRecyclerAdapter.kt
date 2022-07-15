package com.example.kotlinweather.view.weathershow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinweather.databinding.CityItemBinding
import com.example.kotlinweather.domain.DEFAULT_DATE
import com.example.kotlinweather.domain.TEMP_MINUS
import com.example.kotlinweather.domain.TEMP_PLUS
import com.example.kotlinweather.domain.Weather

class CityListRecyclerAdapter(
    private val dataList: List<Weather>,
    private val callback: ChooseCity
) : RecyclerView.Adapter<CityListRecyclerAdapter.WeatherViewHolder>() {
    inner class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = CityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {

        val binding = CityItemBinding.bind(holder.itemView)
        dataList[position].apply {
            city.also { binding.idCityName.text = it.name }

//            if (!dateUpdated.equals(DEFAULT_DATE)){

               if (temperature>=0) {
                   binding.idCityTemp.text = "$dateUpdated $TEMP_PLUS$temperature"
               } else {
                   binding.idCityTemp.text =  "$dateUpdated $TEMP_MINUS$temperature"
               }
//           }




            binding.root.setOnClickListener() {
                callback.onCityClicked(this)
            }
        }
    }

    override fun getItemCount() = dataList.size

}

