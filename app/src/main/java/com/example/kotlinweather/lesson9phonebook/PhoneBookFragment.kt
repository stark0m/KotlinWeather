package com.example.kotlinweather.lesson9phonebook

import android.Manifest
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinweather.databinding.FragmentPhoneBookBinding
import com.example.kotlinweather.domain.PhoneBookContact
import com.example.kotlinweather.model.WeatherCallBack

private const val MANIFEST_PERMISSION_READ_CONTACTS = Manifest.permission.READ_CONTACTS

class PhoneBookFragment : Fragment() {
    private var _binding: FragmentPhoneBookBinding? = null
    private val binding get() = _binding!!
    private val recyclerAdapter: PhoneBookRecyclerAdapter by lazy {
        PhoneBookRecyclerAdapter(
            callBack
        )
    }
    private lateinit var checkPhoneBookPermission: ActivityResultLauncher<String>
    private val callBack = WeatherCallBack<PhoneBookContact> {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${it.contactPhone}")
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPhoneBookPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
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
        val permissionsReadContact = ContextCompat.checkSelfPermission(
            requireContext(), MANIFEST_PERMISSION_READ_CONTACTS
        )

        if (permissionsReadContact == PackageManager.PERMISSION_GRANTED) {
            showContacts()
        } else {
            if (shouldShowRequestPermissionRationale(MANIFEST_PERMISSION_READ_CONTACTS)) {

                AlertDialog
                    .Builder(requireContext())
                    .setMessage("Доступ к контактам необходим для нормального функционирования приложения")
                    .setPositiveButton("Предостваить доступ") { _, _ ->
                        requestContactListPermission()
                    }
                    .setNegativeButton("Отказать") { dialog, _ -> dialog.dismiss();closeFragment() }
                    .create()
                    .show()

            } else {
                requestContactListPermission()
            }
        }


    }

    private fun requestContactListPermission() {
        checkPhoneBookPermission.launch(MANIFEST_PERMISSION_READ_CONTACTS)
    }

    private fun closeFragment() {
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

        val contentResolver = requireContext().contentResolver
        val cursorContentResolver = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursorContentResolver?.let { cursor ->

            if (cursor.moveToFirst()) {
                do {

                    val columnIndexName =
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    val contactName = cursor.getString(columnIndexName)

                    val columnIndexId =
                        cursor.getColumnIndex(ContactsContract.Contacts._ID)
                    val contactId = cursor.getString(columnIndexId)

                    val hasPhoneNumberIndex =
                        cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                    val hasPhoneNumbers = cursor.getInt(hasPhoneNumberIndex)


                    if (hasPhoneNumbers > 0) {
                        showFirstPhoneNumber(contentResolver, contactId, contactName)
                    } else {
                        showContactWitoutPhoneNumber(contactName)
                    }

                } while (cursor.moveToNext())
            }
        }
        cursorContentResolver?.close()
        recyclerAdapter.notifyDataSetChanged()
    }

    private fun showContactWitoutPhoneNumber(contactName: String) {
        recyclerAdapter.addContact(
            PhoneBookContact(contactName)
        )
    }

    private fun showFirstPhoneNumber(
        contentResolver: ContentResolver,
        contactId: String,
        contactName: String
    ) {
        val pCursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
            arrayOf(contactId),
            null
        )

        pCursor?.let { phonesCursor ->
            phonesCursor.moveToFirst()
            val phoneNumberIndex =
                phonesCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val phoneNumber = phonesCursor.getString(phoneNumberIndex)

            recyclerAdapter.addContact(
                PhoneBookContact(
                    contactName,
                    phoneNumber
                )
            )
        }
        pCursor?.close()
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