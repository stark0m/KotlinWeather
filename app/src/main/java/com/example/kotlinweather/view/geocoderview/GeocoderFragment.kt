package com.example.kotlinweather.view.geocoderview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinweather.R
import com.example.kotlinweather.databinding.FragmentGeocoderBinding
import com.example.kotlinweather.databinding.FragmentPhoneBookBinding


class GeocoderFragment : Fragment() {
    private var _binding: FragmentGeocoderBinding? = null
    private val binding get() = _binding!!
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


    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }
    companion object {

        fun newInstance() =
            GeocoderFragment()
    }
}