package com.example.homework10.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homework10.R
import com.example.homework10.activity.MainActivity
import com.example.homework10.database.Note
import com.example.homework10.database.NoteRepository
import com.example.homework10.databinding.FragmentEditNoteBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.*
import java.util.*


class EditNoteFragment : Fragment(R.layout.fragment_edit_note) {

    private var calendar: Calendar? = null
    private lateinit var client: FusedLocationProviderClient
    private lateinit var database: NoteRepository
    private var noteId: Int? = null
    private var longitude: Double? = null
    private var latitude: Double? = null
    private var binding: FragmentEditNoteBinding? = null
    private var scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        client = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditNoteBinding.bind(view)
        database = (requireActivity() as MainActivity).database
        with(binding) {
            this?.toolbar?.apply {
                setNavigationIcon(R.drawable.back)
                setNavigationOnClickListener {
                    findNavController().navigate(R.id.notesFragment)
                }
                setOnMenuItemClickListener { onOptionsItemSelected(it) }
            }
            this?.date?.setOnClickListener {
                showDialog()
            }
            this?.map?.setOnClickListener {
                getLocation()
            }
            this?.save?.setOnClickListener {
                if (allDataExist()) saveNote()
                else {
                    Toast.makeText(
                        context,
                        "Проверьте, что все данные введены корректно",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            setNote()
        }
    }

    //DONE
    private fun showDialog() {
        calendar = Calendar.getInstance()
        val datePickerFragment = DateDialogFragment()
        val supportFragmentManager = requireActivity().supportFragmentManager

        supportFragmentManager.setFragmentResultListener(
            "REQUEST_KEY",
            viewLifecycleOwner
        ) { resultKey, bundle ->
            if (resultKey == "REQUEST_KEY") {
                calendar?.timeInMillis = bundle.getLong("DATE")
            }
        }
        datePickerFragment.show(supportFragmentManager, "DatePickerDialog")
    }

    //DONE
    private fun setNote() {
        arguments?.getInt("NOTE_ID")?.let {
            val getId = it
            scope.launch {
                val note = database.findNoteById(getId)
                binding?.apply {
                    txtTitle.setText(note?.title)
                    txtDescription.setText(note?.description)
                    note?.date?.let {
                        calendar = Calendar.getInstance()
                        calendar?.time = it
                    }
                }
            }
        }

    }

    private fun allDataExist(): Boolean {
        binding?.apply {
            if (txtTitle.text.toString().isEmpty()) {
                Toast.makeText(context, "Добавьте заголовок", Toast.LENGTH_SHORT).show()
                return false
            }
            if (txtDescription.text.toString().isEmpty()) {
                Toast.makeText(context, "Добавьте описание", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    //DONE
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissions() == true) {
            val locationManager =
                activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) or
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            ) {
                client.lastLocation.addOnSuccessListener { location: Location? ->
                    latitude = location?.latitude
                    longitude = location?.longitude
                    if (location != null) {
                        Toast.makeText(context, "Геолокация найдена", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Геолокация не найдена. Проверьте, что все работает исправно",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                startActivity(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        } else {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ), REQUEST_CODE
            )
        }
    }

    //DONE
    private fun checkPermissions(): Boolean? {
        activity?.apply {
            return (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    == PackageManager.PERMISSION_GRANTED)
        }
        return null
    }

    private fun saveNote() {
        noteId?.let {
            updateTask(it)
        }
        if (noteId == null && allDataExist()) {
            binding?.apply {
                val newNote = Note(
                    0,
                    txtTitle.text.toString(),
                    txtDescription.text.toString(),
                    calendar?.time,
                    longitude,
                    latitude
                )
                scope.launch {
                    database.addNote(newNote)
                }
            }
            Toast.makeText(context, "Задание добавлено!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.notesFragment)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            getLocation()
        } else {
            Toast.makeText(context, "Вы не дали доступ к геолокации", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTask(id: Int) {
        if (allDataExist()) {
            scope.launch { updateData(id) }
            Toast.makeText(context, "Задание обновлено!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.notesFragment)
        }
    }

    private suspend fun updateData(id: Int) {
        val note = database.findNoteById(id)
        binding?.apply {
            note?.let {
                it.title = txtTitle.text.toString()
                it.description = txtDescription.text.toString()
                calendar?.apply {
                    it.date = time
                }
                database.updateNote(note)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        scope.cancel()
    }

    companion object {
        private const val REQUEST_CODE = 1
    }
}