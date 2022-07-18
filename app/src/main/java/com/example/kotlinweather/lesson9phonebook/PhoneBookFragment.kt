package com.example.kotlinweather.lesson9phonebook

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinweather.databinding.FragmentPhoneBookBinding

class PhoneBookFragment : Fragment() {
    private var _binding: FragmentPhoneBookBinding? = null
    private val binding get() = _binding!!
    private val recyclerAdapter: PhoneBookRecyclerAdapter by lazy { PhoneBookRecyclerAdapter() }
    private lateinit var checkPhoneBookPermission: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPhoneBookPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                showContacts()
            } else {
                infrormCustomerAboutDeclinePhoneBookAccess()
            }

        }
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

        checkPhoneBookAccessPermitions()
    }

    private fun checkPhoneBookAccessPermitions() {
        requireContext().let {
            if (ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_CONTACTS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                showContacts()
            } else

                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {

                    AlertDialog
                        .Builder(it)
                        .setMessage("Доступ к контактам необходим для нормального функционирования приложения")
                        .setPositiveButton("Предостваить доступ") { _, _ ->
                            requestContactListPermission()
                        }
                        .setNegativeButton("Отказать") { dialog, _ -> dialog.dismiss() ;closeFragment() }
                        .create()
                        .show()


                } else {
                    requestContactListPermission()
                }

        }

    }

    private fun requestContactListPermission() {

        checkPhoneBookPermission.launch(Manifest.permission.READ_CONTACTS)
    }

    private fun closeFragment(){
        requireActivity().supportFragmentManager.popBackStack()
    }




    private fun infrormCustomerAboutDeclinePhoneBookAccess() {
        AlertDialog.Builder(requireContext()).setTitle("Дотсуп к контатам")
            .setNegativeButton("Доступ закрыт, приложение не может отобразить список контактов") { dialog, _ ->
                dialog.dismiss()
                closeFragment()
            }
            .create()
            .show()
    }

    private fun showContacts() {

        TODO()
    }

    private fun initRecycler() {
        binding.phoneBookRecycler.layoutManager = LinearLayoutManager(requireContext())
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