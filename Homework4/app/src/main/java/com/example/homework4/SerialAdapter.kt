package com.example.homework4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.homework4.databinding.ItemSerialBinding

class SerialAdapter(
    private var list: List<Serial>,
    private val glide: RequestManager,
    private val onItemClick: (Serial) -> Unit
) : RecyclerView.Adapter<SerialHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SerialHolder = SerialHolder(
        binding = ItemSerialBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        glide = glide,
        onItemClick = onItemClick
    )

    override fun onBindViewHolder(
        holder: SerialHolder,
        position: Int
    ) {
        holder.onBind(list[position])

    }

//    fun remove(position: Int) {
//        list.removeAt(position)
//        notifyItemRemoved(position)
//    }
//
//    fun add(text: String, position: Int) {
//        list.add(position, text)
//        notifyItemInserted(position)
//    }
    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<Serial>) {
        list = newList
        notifyDataSetChanged()
    }

}