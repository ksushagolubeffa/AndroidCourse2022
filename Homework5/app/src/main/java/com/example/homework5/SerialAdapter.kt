package com.example.homework5

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.homework5.model.Item


class SerialAdapter(
    private var list: ArrayList<Item>,
    private val glide: RequestManager,
    private val onItemClick: (Item.Serial) -> Unit,
    private val onDeleteClick: ((Int)-> Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = when(viewType){
        R.layout.item_ad-> AdHolder.create(parent, glide)
        R.layout.item_serial -> SerialHolder.create(parent, glide, action1 = onItemClick, action2 = onDeleteClick)
        else -> throw IllegalStateException()
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (val item = list[position]) {
            is Item.Serial -> (holder as SerialHolder).onBind(list[position] as Item.Serial)
            is Item.Ad -> (holder as AdHolder).onBind(text = item.text, adress = item.adress, url = item.url)
        }

    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is Item.Ad -> R.layout.item_ad
            is Item.Serial -> R.layout.item_serial
        }
    }

    override fun getItemId(position: Int): Long {
        return when (val item = list[position]) {
            is Item.Serial -> item.id.toLong()
            is Item.Ad -> super.getItemId(position)
        }
    }

    fun updateData(newList: ArrayList<Item>) {
        list = newList
        notifyDataSetChanged()
    }


}