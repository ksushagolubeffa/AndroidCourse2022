package com.example.homework4

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.homework4.databinding.ItemSerialBinding

class SerialHolder (
    private val binding: ItemSerialBinding,
    private val glide: RequestManager,
    private val onItemClick: (Serial) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private val options: RequestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

    fun onBind(serial: Serial) {
        with(binding) {
            txtName.text = serial.name
            txtYear.text = serial.year
            root.setOnClickListener {
                onItemClick(serial)
            }
            glide
                .load(serial.url)
                .placeholder(R.drawable.netflix)
                .error(R.drawable.netflix)
                .into(ivNetflix)
        }
    }
}