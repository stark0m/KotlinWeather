package com.example.kotlinweather.lesson9phonebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinweather.databinding.PhoneBookOneContactBinding
import com.example.kotlinweather.domain.PhoneBookContact
import com.example.kotlinweather.model.WeatherCallBack

class PhoneBookRecyclerAdapter(val callBack: WeatherCallBack<PhoneBookContact>) : RecyclerView.Adapter<PhoneBookRecyclerAdapter.ViewHolder>() {
    private val contactList = mutableListOf<PhoneBookContact>()

    fun addContact(contact: PhoneBookContact){
        contactList.add(contact)
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