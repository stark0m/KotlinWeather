package com.example.kotlinweather.view.geocoderview

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinweather.R
import com.example.kotlinweather.databinding.FragmentGeocoderBinding
import com.example.kotlinweather.databinding.FragmentPhoneBookBinding
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.model.AppCallback
import com.example.kotlinweather.model.geocoder.RepositoreGeocoderImpl
import com.example.kotlinweather.model.geocoder.RepositoryGeocoder
import com.example.kotlinweather.view.weathershow.WeatherShowViewModel


class GeocoderFragment : Fragment() {
    private var _binding: FragmentGeocoderBinding? = null
    private val binding get() = _binding!!
    private val viewModelWeatherShow: WeatherShowViewModel by lazy {
        ViewModelProvider(requireActivity()).get(WeatherShowViewModel::class.java)
    }
    private val geocoderRepository: RepositoryGeocoder by lazy {
        RepositoreGeocoderImpl()
    }

    private val addButtonClick = AppCallback<Weather> {

        AlertDialog.Builder(requireContext())
            .setTitle("Добавить ${it.city.name} в текущий список локаций ?")
            .setPositiveButton("Добавить"){_,_->
                viewModelWeatherShow.addCityToCurrentList(it)

            }
            .setNegativeButton("Отмена"){dialog,_->dialog.dismiss()}
            .show()


    }
    private val showMapButtonClick = AppCallback<Weather> {
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
        binding.locationsRecycler.layoutManager = LinearLayoutManager(requireContext())
        initListeners()
    }

    private fun initListeners() {
        binding.enteredAddress.addTextChangedListener { enteredLocation ->
            enteredLocation?.let {
                    if (it.toString() != "") {
                        geocoderRepository.getLocationList(it.toString()) {
                            binding.locationsRecycler.adapter = GeocoderRecyclerAdapter(
                                it.toMutableList(),
                                addButtonClick,
                                showMapButtonClick
                            )
                        }
                    }



            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        fun newInstance() =
            GeocoderFragment()
    }
}