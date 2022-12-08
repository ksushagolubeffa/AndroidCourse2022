package com.example.homework9.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework9.model.Song
import com.example.homework9.databinding.ItemSongBinding

class SongAdapter(
    private var list: List<Song>,
    private val onItemClick: (Song) -> Unit
) : RecyclerView.Adapter<SongHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder = SongHolder(
        binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onItemClick = onItemClick
    )

    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        holder.onBind(list[position])
    }
    override fun getItemCount(): Int = list.size

}