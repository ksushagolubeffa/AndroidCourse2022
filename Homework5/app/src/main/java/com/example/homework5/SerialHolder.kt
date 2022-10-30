package com.example.homework5


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.homework5.databinding.ItemSerialBinding
import com.example.homework5.model.Item
import com.example.homework5.model.Serial
import com.example.homework5.model.SerialRepository

class SerialHolder(
    private val binding: ItemSerialBinding,
    private val glide: RequestManager,
    private val onItemClick: (Item.Serial) -> Unit,
    private val onDeleteClick: ((Int)-> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    private var listAdapter: SerialsListAdapter? = null

    private val options: RequestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

    fun onBind(serial: Item.Serial) {
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
            btnDelete.setOnClickListener{
               onDeleteClick?.invoke(adapterPosition)
            }
        }
    }

    companion object {

        const val ARG_FAVORITE = "arg_favorite"
        const val ARG_NAME = "arg_name"

        fun create(
            parent: ViewGroup,
            glide: RequestManager,
            action1: (Item.Serial) -> Unit,
            action2: (Int)-> Unit
        ): SerialHolder = SerialHolder(
            binding = ItemSerialBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide = glide,
            onItemClick = action1,
            onDeleteClick = action2
        )
    }
}