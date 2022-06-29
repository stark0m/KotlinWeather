package com.example.kotlinweather.view.weathershow

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinweather.R
import com.example.kotlinweather.databinding.WeatherShowFragmentBinding
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.view.onecityview.base_fragment.OneCItyWeatherViewFragment
import com.example.kotlinweather.view.onecityview.dialog_fragment.OneCityWeatherViewDialog
import com.example.kotlinweather.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

class WeatherShowFragment : Fragment() {
    private var _binding: WeatherShowFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var recyclerAdapter:CityListRecyclerAdapter
    private lateinit var viewModelWeatherShow: WeatherShowViewModel
    lateinit var clickWeatherListener:ChooseCity



    companion object {
        fun newInstance() = WeatherShowFragment()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WeatherShowFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelWeatherShow = ViewModelProvider(this).get(WeatherShowViewModel::class.java)
        viewModelWeatherShow.getObserver().observe(viewLifecycleOwner) { showData(it) }
        viewModelWeatherShow.getWeatherList()

        initListeners()
        initRecyclerVIew()



    }

    private fun initListeners() {
        /**
         * clickWeatherListener - действие по нажатию на элемент списка городов
         */
        clickWeatherListener = ChooseCity { weather ->
            viewModelWeatherShow.tryToShowWeather(weather)
        }

        binding.floatButtonId.setOnClickListener(){
            viewModelWeatherShow.getAnotherCityList()
        }
    }

    private fun initRecyclerVIew() {

        binding.idRecyclerView.adapter = CityListRecyclerAdapter(listOf()){}
        binding.idRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

    }

    private fun showData(state: AppState) {
        when (state) {
            is AppState.Error -> {


                    Snackbar
                        .make(binding.mainView, state.error.message.toString(), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Reload") { viewModelWeatherShow.getWeatherList() }
                        .show()

                binding.progress.visibility = View.GONE

            }
            AppState.Loading -> {
                binding.progress.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.progress.visibility = View.GONE
                Snackbar.make(binding.mainView, "Success", Snackbar.LENGTH_LONG).show()
            }
            is AppState.ReceivedCityListSuccess -> {
                updateCityList(state.cityList)
                binding.progress.visibility = View.GONE
                Snackbar.make(binding.mainView, "Success loaded list", Snackbar.LENGTH_LONG).show()
            }
            is AppState.ShowWeater -> {
                binding.progress.visibility = View.GONE

                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .hide(this)
                    .add(R.id.container,OneCItyWeatherViewFragment.newInstance(state.weather))
                    .addToBackStack("")
                    .commit()

            //                val modalBottomSheet = OneCityWeatherViewDialog(state.weather)
//                modalBottomSheet.show(parentFragmentManager, OneCityWeatherViewDialog.TAG)
            }

            else -> {}
        }


    }

    private fun updateCityList(list:List<Weather>){

        binding.idRecyclerView.adapter = CityListRecyclerAdapter(list,clickWeatherListener)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}