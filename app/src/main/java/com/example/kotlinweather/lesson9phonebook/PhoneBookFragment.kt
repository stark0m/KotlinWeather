package com.example.kotlinweather.lesson9phonebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinweather.R
import com.example.kotlinweather.databinding.FragmentPhoneBookBinding

class PhoneBookFragment : Fragment() {
    private var _binding: FragmentPhoneBookBinding? = null
    private val binding get() = _binding!!
    private val recyclerAdapter:PhoneBookRecyclerAdapter by lazy { PhoneBookRecyclerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhoneBookBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        binding.phoneBookRecycler.layoutManager=LinearLayoutManager(requireContext())
        binding.phoneBookRecycler.adapter = recyclerAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() =
            PhoneBookFragment()
    }

}