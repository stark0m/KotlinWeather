package com.example.kotlinweather.view.weathershow

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinweather.R
import com.example.kotlinweather.databinding.WeatherShowFragmentBinding
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.lesson9phonebook.PhoneBookFragment
import com.example.kotlinweather.view.geocoderview.GeocoderFragment
import com.example.kotlinweather.view.onecityview.basefragment.OneCItyWeatherViewFragment
import com.example.kotlinweather.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

class WeatherShowFragment : Fragment() {
    private var _binding: WeatherShowFragmentBinding? = null
    private val binding get() = _binding!!
//    private val viewModelWeatherShow: WeatherShowViewModel by activityViewModels()
    private val viewModelWeatherShow: WeatherShowViewModel by lazy {
        ViewModelProvider(requireActivity()).get(WeatherShowViewModel::class.java)
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            p1?.let { Toast.makeText(requireContext(), "${it.action}", Toast.LENGTH_SHORT).show() }

        }

    }


    private lateinit var clickWeatherListener: ChooseCity


    companion object {
        fun newInstance() = WeatherShowFragment()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.topbarmenu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WeatherShowFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)
        return view
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.phone_book ->{
                showPhoneBookFragment()
            }
            else ->{}
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showPhoneBookFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .hide(this)
            .add(R.id.container,PhoneBookFragment.newInstance())
            .addToBackStack("")
            .commit()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelWeatherShow.getObserver().observe(viewLifecycleOwner) { showData(it) }

        viewModelWeatherShow.getWeatherList()
        initListeners()
        initRecyclerVIew()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().registerReceiver(
            broadcastReceiver,
            IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    private fun initListeners() {
        /**
         * clickWeatherListener - действие по нажатию на элемент списка городов
         */
        clickWeatherListener = ChooseCity { weather ->
            viewModelWeatherShow.tryToShowWeather(weather)
        }

        binding.floatButtonId.setOnClickListener {
            viewModelWeatherShow.getAnotherCityList()
        }
        binding.floatButtonAddCity.setOnClickListener{
            viewModelWeatherShow.tryToShowGeocoder()
        }
    }

    private fun initRecyclerVIew() {

        binding.idRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

    }

    private fun showData(state: AppState) {
        when (state) {
            is AppState.Error -> {


                binding.progress.visibility = View.GONE
                binding.root.showErrorWithAction(
                    R.string.repo_error,
                    Snackbar.LENGTH_INDEFINITE,
                    "Reload"
                ) {
                    viewModelWeatherShow.getWeatherList()
                }

            }
            AppState.Loading -> {
                binding.progress.visibility = View.VISIBLE
            }
            AppState.ShowGeocoder -> {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .hide(this)
                    .add(R.id.container, GeocoderFragment.newInstance())
                    .addToBackStack("")
                    .commit()
            }
            is AppState.Success -> {
                binding.progress.visibility = View.GONE
                Snackbar.make(binding.mainView, "Success", Snackbar.LENGTH_LONG).show()
            }
            is AppState.ReceivedCityListSuccess -> {
                updateCityList(state.cityList)
                binding.progress.visibility = View.GONE

                Toast.makeText(requireContext(), "Success loaded list", Toast.LENGTH_SHORT).show()
            }
            is AppState.ShowWeater -> {
                binding.progress.visibility = View.GONE

                /**
                 * вызов делаем для выполнения 2го задания 6 урока, закоментировать в будующем
                 */
//                requireActivity().supportFragmentManager
//                    .beginTransaction()
//                    .hide(this)
//                    .add(R.id.container, Lesson6Fragment.newInstance(state.weather))
//                    .addToBackStack("")
//                    .commit()

                /**
                 * для работы с правильной архитектурой разблокировать необходимо вызов ниже,
                 */
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .hide(this)
                    .add(R.id.container, OneCItyWeatherViewFragment.newInstance(state.weather))
                    .addToBackStack("")
                    .commit()


                /**
                 * можно активировать модальное окно вместо фрагмента для отображения информации о погоде
                 */
                //                val modalBottomSheet = OneCityWeatherViewDialog(state.weather)
                //                modalBottomSheet.show(parentFragmentManager, OneCityWeatherViewDialog.TAG)
            }

            else -> {
                /**
                 * т.к. модель используется несколькими вью, класс расширился и есть состояния которые нет смысла обрабатывать в этом фрагменте
                 */
            }
        }


    }


    /**
     * перегружаем функцию, теперь возможно передать сюда строковые ресурсы
     */
    private fun View.showErrorWithAction(
        message: Int,
        duration: Int,
        actionText: String,
        block: (v: View) -> Unit
    ) {
        Snackbar
            .make(
                binding.mainView,
                message,
                duration
            )
            .setAction(actionText, block)
            .show()


    }


    private fun updateCityList(list: List<Weather>) {

        binding.idRecyclerView.adapter = CityListRecyclerAdapter(list, clickWeatherListener)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        requireContext().unregisterReceiver(broadcastReceiver)
    }
}