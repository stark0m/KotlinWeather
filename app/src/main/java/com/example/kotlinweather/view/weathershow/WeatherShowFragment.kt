package com.example.kotlinweather.view.weathershow

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinweather.databinding.WeatherShowFragmentBinding
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

class WeatherShowFragment : Fragment() {
    private var _binding: WeatherShowFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var recyclerAdapter:CityListRecyclerAdapter
    private lateinit var viewModelWeatherShow: WeatherShowViewModel




    companion object {
        fun newInstance() = WeatherShowFragment()
    }



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
        viewModelWeatherShow.getObserver().observe(viewLifecycleOwner) { showData(it) }
        viewModelWeatherShow.getData()

        initRecyclerVIew()


    }

    private fun initRecyclerVIew() {
        recyclerAdapter = CityListRecyclerAdapter(listOf()){}
        binding.idRecyclerView.adapter = recyclerAdapter
        binding.idRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

    }

    private fun showData(state: AppState) {
        when (state) {
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
//                redrawWeather(state.weatherData)
                Snackbar.make(binding.mainView, "Success", Snackbar.LENGTH_LONG).show()
            }
            is AppState.ReceivedCityListSuccess -> {
                updateCityList(state.cityList)
                binding.progress.visibility = View.GONE
                Snackbar.make(binding.mainView, "Success loaded list", Snackbar.LENGTH_LONG).show()
            }
        }


    }

    private fun updateCityList(list:List<Weather>){
        recyclerAdapter = CityListRecyclerAdapter(list){
            Toast.makeText(requireContext(), "CLICKED $it", Toast.LENGTH_SHORT).show()
        }

        recyclerAdapter.notifyDataSetChanged()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}