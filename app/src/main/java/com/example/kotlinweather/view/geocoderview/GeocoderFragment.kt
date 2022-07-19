package com.example.kotlinweather.view.geocoderview

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinweather.databinding.FragmentGeocoderBinding
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.model.AppCallback
import com.example.kotlinweather.model.geocoder.RepositoreGeocoderImpl
import com.example.kotlinweather.model.geocoder.RepositoryGeocoder
import com.example.kotlinweather.view.weathershow.WeatherShowViewModel

private const val GPS_ACCESS_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION

class GeocoderFragment : Fragment() {
    private var _binding: FragmentGeocoderBinding? = null
    private val binding get() = _binding!!

    private val checkGPSPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                showMapByLocalCoordinates()

            } else {
                infrormCustomerAboutDeclineGPRSAccess()
            }

        }

    private fun showMapByLocalCoordinates() {
        geocoderRepository.getGPSLocation {
            it?.let {
                viewModelWeatherShow.openGoogleMap(it.latitude, it.longitude)
            }
        }
    }

    private fun infrormCustomerAboutDeclineGPRSAccess() {
        AlertDialog.Builder(requireContext())
            .setTitle("Отсутствует доступ к гео данным, включите геоданные")
            .setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss() }.show()
    }

    private val viewModelWeatherShow: WeatherShowViewModel by lazy {
        ViewModelProvider(requireActivity()).get(WeatherShowViewModel::class.java)
    }

    private val geocoderRepository: RepositoryGeocoder by lazy {
        RepositoreGeocoderImpl()
    }

    private val addButtonClick = AppCallback<Weather> {

        AlertDialog.Builder(requireContext())
            .setTitle("Добавить ${it.city.name} в текущий список локаций ?")
            .setPositiveButton("Добавить") { _, _ ->
                viewModelWeatherShow.addCityToCurrentList(it)

            }
            .setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private val showMapButtonClick = AppCallback<Weather> {
        showMapByCoordinates(it.city.lat,it.city.lon)
    }

    private fun showMapByCoordinates(lat: Double, lon: Double) {
        viewModelWeatherShow.openGoogleMap(lat, lon)
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

        binding.myLocationButton.setOnClickListener{
            val isGPRSAccess = ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            )


            if (isGPRSAccess==PERMISSION_GRANTED){
                showMapByLocalCoordinates()
            } else {
                checkGPSPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
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