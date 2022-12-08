package com.example.homework9.rv

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.homework9.R
import com.example.homework9.model.Song
import com.example.homework9.databinding.ItemSongBinding

class SongHolder (
    private val binding: ItemSongBinding,
    private val onItemClick: (Song) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val options: RequestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

    fun onBind(song: Song) {
        with(binding) {
            txtName.text = song.name
            txtSinger.text = song.singer
            ivSong.setImageResource(song.cover)
            root.setOnClickListener {
                onItemClick(song)
            }
            if(song.colorID !=0){
                layoutItem.setBackgroundResource(R.color.click)
            }
        }
    }

}