package com.example.kotlinweather.view.map

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinweather.R
import com.example.kotlinweather.domain.City
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.model.geocoder.RepositoreGeocoderImpl
import com.example.kotlinweather.model.geocoder.RepositoryGeocoder
import com.example.kotlinweather.view.weathershow.WeatherShowViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


private const val LATLANG = "LATLANG"

class GoogleMapFragment : Fragment() {
    val handler = Handler(Looper.getMainLooper())
    private lateinit var latLng: LatLng
    private val viewModelWeatherShow: WeatherShowViewModel by lazy {
        ViewModelProvider(requireActivity()).get(WeatherShowViewModel::class.java)
    }
    private val repositoreGeocoderImpl: RepositoryGeocoder by lazy { RepositoreGeocoderImpl() }
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        googleMap.addMarker(MarkerOptions().position(latLng).title("Marker "))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.setOnMapClickListener {

        }
    }

    val longClickListener = GoogleMap.OnMapLongClickListener {
        val lat = it.latitude
        val lon = it.longitude
        Thread {
            val locationName = repositoreGeocoderImpl.getLocationByCoordinates(lat, lon)


            handler.post{
                AlertDialog.Builder(requireContext())
                    .setTitle("Добавить $locationName в текущий список локаций?")
                    .setNegativeButton("Нет") { dialog, _ -> dialog.dismiss() }
                    .setPositiveButton("Добавить") { _, _ ->
                        viewModelWeatherShow.addCityToCurrentList(Weather(City(locationName, lat, lon)))

                    }.show()
            }


        }.start()







    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_google_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            latLng = it.getParcelable(LATLANG)!!
        }


        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { map ->
            map.addMarker(MarkerOptions().position(latLng).title("Marker "))
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            map.setOnMapLongClickListener(longClickListener)

        }


    }

    companion object {
        fun newInstance(latLng: LatLng) = GoogleMapFragment().apply {
            arguments = Bundle().apply {
                putParcelable(LATLANG, latLng)
            }

        }
    }
}