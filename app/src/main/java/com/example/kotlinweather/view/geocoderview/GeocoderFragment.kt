package com.example.kotlinweather.view.geocoderview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinweather.R
import com.example.kotlinweather.databinding.FragmentGeocoderBinding
import com.example.kotlinweather.databinding.FragmentPhoneBookBinding
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.model.AppCallback
import com.example.kotlinweather.view.weathershow.WeatherShowViewModel


class GeocoderFragment : Fragment() {
    private var _binding: FragmentGeocoderBinding? = null
    private val binding get() = _binding!!
    private val viewModelWeatherShow: WeatherShowViewModel by lazy {
        ViewModelProvider(requireActivity()).get(WeatherShowViewModel::class.java)
    }
    
    private val addButtonClick = AppCallback<Weather>{
        viewModelWeatherShow.addCityToCurrentList(it)
    }
    private val showMapButtonClick = AppCallback<Weather>{
        // TODO:  
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGeocoderBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.enteredAddress.addTextChangedListener {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }
    companion object {

        fun newInstance() =
            GeocoderFragment()
    }
}