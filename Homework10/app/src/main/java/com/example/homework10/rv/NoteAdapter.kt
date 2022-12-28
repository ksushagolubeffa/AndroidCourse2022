package com.example.homework10.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.homework10.database.Note

class NoteAdapter(
    private val onItemClick: (Int) -> Unit,
    private val onDeleteClick: ((Int) -> Unit)
) : ListAdapter<Note, NoteHolder>(
    object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(
            oldItem: Note,
            newItem: Note
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Note,
            newItem: Note
        ): Boolean = oldItem == newItem
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder =
        NoteHolder.create(parent, onItemClick, onDeleteClick)

    override fun onBindViewHolder(holder: NoteHolder, position: Int) =
        holder.onBind(getItem(position))

    override fun submitList(list: MutableList<Note>?) {
        super.submitList(
            if (list == null) null
            else ArrayList(list)
        )
    }
}