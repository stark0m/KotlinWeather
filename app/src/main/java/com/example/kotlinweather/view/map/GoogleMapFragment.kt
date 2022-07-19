package com.example.kotlinweather.view.map

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinweather.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


private const val LATLANG = "LATLANG"
class GoogleMapFragment : Fragment() {

    private lateinit var latLng:LatLng
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

        arguments?.let{
            latLng = it.getParcelable(LATLANG)!!
        }


        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync{
            it.addMarker(MarkerOptions().position(latLng).title("Marker "))
            it.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        }


    }

    companion object{
        fun newInstance(latLng:LatLng)=GoogleMapFragment().apply  {
            arguments = Bundle().apply {
                putParcelable(LATLANG,latLng)
            }

        }
    }
}