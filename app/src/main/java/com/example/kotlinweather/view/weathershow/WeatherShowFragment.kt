package com.example.kotlinweather.view.weathershow

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinweather.databinding.WeatherShowFragmentBinding
import com.example.kotlinweather.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

class WeatherShowFragment : Fragment() {
    private var _binding: WeatherShowFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = WeatherShowFragment()
    }

    private lateinit var viewModelWeatherShow: WeatherShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WeatherShowFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelWeatherShow = ViewModelProvider(this).get(WeatherShowViewModel::class.java)
        viewModelWeatherShow.getObserver().observe(viewLifecycleOwner) {

            when (it) {
                is AppState.Error -> {

                    binding.progress.visibility = View.GONE
                    Snackbar
                        .make(binding.mainView, "Error", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Reload") { viewModelWeatherShow.getData() }
                        .show()
                }
                AppState.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
                is AppState.Success -> {
                    binding.progress.visibility = View.GONE
                    Snackbar.make(binding.mainView, "Success", Snackbar.LENGTH_LONG).show()
                }
            }
        }
        viewModelWeatherShow.getData()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}