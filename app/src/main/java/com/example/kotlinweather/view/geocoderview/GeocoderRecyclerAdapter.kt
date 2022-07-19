package com.example.kotlinweather.view.geocoderview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinweather.databinding.PhoneBookOneContactBinding
import com.example.kotlinweather.domain.PhoneBookContact
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.model.WeatherCallBack

class GeocoderRecyclerAdapter(val callBack: WeatherCallBack<PhoneBookContact>) : RecyclerView.Adapter<GeocoderRecyclerAdapter.ViewHolder>() {
    private val contactList = mutableListOf<Weather>()

    fun addLocation(weather:Weather){

        contactList.add(weather)
    }

    fun clearAdapterList(){
        contactList.clear()
    }
    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PhoneBookOneContactBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = PhoneBookOneContactBinding.bind(holder.itemView)
        contactList[position].let { contact->
            binding.idContactName.text = contact.contactName
            binding.idContactPhone.text= contact.contactPhone
            holder.itemView.setOnClickListener(){
                contact.run {
                    callBack.onDataReceived(PhoneBookContact(this.contactName,this.contactPhone))
                }

            }
        }

    }

    override fun getItemCount()= contactList.size
}