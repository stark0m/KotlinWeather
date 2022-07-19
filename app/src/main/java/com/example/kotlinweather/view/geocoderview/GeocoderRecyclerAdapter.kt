package com.example.kotlinweather.view.geocoderview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinweather.databinding.LocationOneItemBinding
import com.example.kotlinweather.domain.PhoneBookContact
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.model.AppCallback
import com.example.kotlinweather.model.WeatherCallBack

class GeocoderRecyclerAdapter(private val addButton: AppCallback<Weather>,private val showMapButton:AppCallback<Weather>) : RecyclerView.Adapter<GeocoderRecyclerAdapter.ViewHolder>() {
    private val locationList = mutableListOf<Weather>()

    fun addLocation(weather:Weather){

        locationList.add(weather)
    }

    fun clearAdapterList(){
        locationList.clear()
    }
    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LocationOneItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = LocationOneItemBinding.bind(holder.itemView)
        locationList[position].let { weather->
            binding.addToCustomListButton.setOnClickListener{addButton.onClick(weather)}
            binding.idShowMapButton.setOnClickListener{showMapButton.onClick(weather)}
            binding.idLocationName.text = weather.city.name
            binding.idCoordLatLon.text = "lat:${weather.city.lat}  lon:${weather.city.lon}"

        }

    }

    override fun getItemCount()= locationList.size
}