package com.example.homework10.rv

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework10.database.DateConverter
import com.example.homework10.database.Note
import com.example.homework10.databinding.ItemGridBinding
import com.example.homework10.databinding.ItemLinearBinding
import java.text.SimpleDateFormat

class NoteHolder(
    private val binding: ItemGridBinding,
    private val onItemClick: (Int) -> Unit,
    private val onDeleteClick: ((Int) -> Unit)
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    fun onBind(note: Note) {
        with(binding) {
            //title
            txtTitle.text = note.title
            //description
            txtDescription.text = note.description
            //data
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                txtDate.text = note.date?.let { SimpleDateFormat("dd.MM.yyyy").format(it) }
            } else {
                txtDate.text = ""
            }
            //latitude
            if (note.latitude != null) {
                txtLatitude.text = "Latitude: ${note.latitude.toString()}"
            } else {
                txtLatitude.visibility = View.GONE
//                txtLatitude.text = "Latitude: not chosen"
            }
            //longitude
            if (note.longitude != null) {
                txtLongitude.text = "Longitude: ${note.longitude.toString()}"
            } else {
                txtLongitude.visibility = View.GONE
//                txtLongitude.text = "Longitude: not chosen"
            }
            root.setOnClickListener {
                onItemClick.invoke(note.id)
            }
            deleteNote.setOnClickListener {
                onDeleteClick.invoke(note.id)
            }
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            action1: (Int) -> Unit,
            action2: (Int) -> Unit
        ): NoteHolder = NoteHolder(
            binding = ItemGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick = action1,
            onDeleteClick = action2
        )
    }
}