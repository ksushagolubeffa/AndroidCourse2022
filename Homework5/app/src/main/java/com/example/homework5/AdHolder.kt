package com.example.homework5

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.homework5.databinding.ItemAdBinding
import com.example.homework5.model.Item

class AdHolder(
    private val binding: ItemAdBinding,
    private val glide: RequestManager
    ): RecyclerView.ViewHolder(binding.root){

        fun onBind(text: String, adress: String, url: String) {
            with(binding) {
                this.tvText.text = text
                this.tvAdress.text = adress
                glide
                    .load(url)
                    .placeholder(R.drawable.ad)
                    .error(R.drawable.ad)
                    .into(imageView)
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            glide: RequestManager
        ): AdHolder = AdHolder(
            binding = ItemAdBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide = glide
        )
    }
}