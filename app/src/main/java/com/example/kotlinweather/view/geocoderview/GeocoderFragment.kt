package com.example.kotlinweather.view.geocoderview

import android.app.AlertDialog
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinweather.R
import com.example.kotlinweather.databinding.FragmentGeocoderBinding
import com.example.kotlinweather.domain.GPS_ACCESS_PERMISSION
import com.example.kotlinweather.domain.StactiFun
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.model.AppCallback
import com.example.kotlinweather.model.geocoder.RepositoreGeocoderImpl
import com.example.kotlinweather.model.geocoder.RepositoryGeocoder
import com.example.kotlinweather.view.map.GoogleMapFragment
import com.example.kotlinweather.view.weathershow.WeatherShowViewModel
import com.example.kotlinweather.viewmodel.AppState
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar


class GeocoderFragment : Fragment() {
    private var _binding: FragmentGeocoderBinding? = null
    private val binding get() = _binding!!



    private val tryToshowMapWithLocalCoordinates =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                showMapByLocalCoordinates()

            } else {
                StactiFun.infrormCustomerAboutDeclineGPRSAccess(requireContext())
            }
        }

    private val viewModelWeatherShow: WeatherShowViewModel by lazy {
        ViewModelProvider(requireActivity()).get(WeatherShowViewModel::class.java)
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

    private fun showMapByLocalCoordinates() {
        viewModelWeatherShow.tryToShowLocaleLocation()

    }

    private fun showMapByCoordinates(lat: Double, lon: Double) {
        viewModelWeatherShow.openGoogleMap(lat, lon)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGeocoderBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        initVMObserver()
        initRecyclerView()
        initListeners()
    }

    private fun initVMObserver() {
        viewModelWeatherShow.getObserver().observe(viewLifecycleOwner) { appState ->

            when (appState) {
                is AppState.ShowMapOn -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(
                            R.id.container,
                            GoogleMapFragment.newInstance(LatLng(appState.lat, appState.lon))
                        )
                        .addToBackStack("")
                        .commit()

                }
                else -> {}
            }

        }
    }

    private fun initRecyclerView() {
        binding.locationsRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initListeners() {
        binding.enteredAddress.addTextChangedListener { enteredLocation ->


            if (!viewModelWeatherShow.isOnline(requireContext())){
                noInternetConnectionInfo()
                return@addTextChangedListener
            }


            enteredLocation?.let {
                if (it.toString() != "") {
                    viewModelWeatherShow.getGeocoder().getLocationList(it.toString()) {
                        binding.locationsRecycler.adapter = GeocoderRecyclerAdapter(
                            it,
                            addButtonClick,
                            showMapButtonClick
                        )
                    }
                }


            }

        }

        binding.myLocationButton.setOnClickListener{

            val isGPRSAccess = ContextCompat.checkSelfPermission(
                requireContext(), GPS_ACCESS_PERMISSION
            )


            if (isGPRSAccess==PERMISSION_GRANTED){
                showMapByLocalCoordinates()
            } else {
                tryToshowMapWithLocalCoordinates.launch(GPS_ACCESS_PERMISSION)
            }
        }
    }

    private fun noInternetConnectionInfo() {
        Snackbar.make(requireView(),"Отсуствует подключене к интернету",Snackbar.LENGTH_SHORT).show()
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