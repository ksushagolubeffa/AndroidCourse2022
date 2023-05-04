package com.example.homework10.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework10.R
import com.example.homework10.activity.MainActivity
import com.example.homework10.database.Note
import com.example.homework10.database.NoteRepository
import com.example.homework10.databinding.FragmentNotesGridBinding
import com.example.homework10.databinding.FragmentNotesLinearBinding
import com.example.homework10.rv.NoteAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NotesFragment : Fragment(R.layout.fragment_notes_grid) {

    private var lightTheme: Boolean = true
    private var isBindingGrid: Boolean = true
    private var bindingGrid: FragmentNotesGridBinding? = null
    private var bindingLinear: FragmentNotesLinearBinding? = null
    private var noteAdapter: NoteAdapter? = null
    private lateinit var database: NoteRepository
    private lateinit var noteList: List<Note>
    private var scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingGrid = FragmentNotesGridBinding.bind(view)
        database = (requireActivity() as MainActivity).database

        noteAdapter = NoteAdapter(onItemClick = {
            var bundle: Bundle?
            id.also {
                bundle = Bundle().apply {
                    putInt("TASK_ID", id)
                }
            }
            findNavController().navigate(R.id.action_notesFragment_to_editNoteFragment, bundle)
        },
            onDeleteClick = { deleteNote(it) })

        val pref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        isBindingGrid = pref.getBoolean("layout", true)
        bindingGrid?.apply {
            toolbar.setOnMenuItemClickListener {
                onOptionsItemSelected(it)
            }
            this.addBtn.setOnClickListener {
                var bundle: Bundle?
                id.also {
                    bundle = Bundle().apply {
                        putInt("TASK_ID", id)
                    }
                }
                findNavController().navigate(R.id.action_notesFragment_to_editNoteFragment, bundle)
            }
            this.rvNote.run {
                adapter = noteAdapter
                if (isBindingGrid) this.layoutManager = GridLayoutManager(context, 2)
                else this.layoutManager = LinearLayoutManager(context)
            }
        }
        scope.launch(Dispatchers.Default) { updateTasks() }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deleteAll -> {
                deleteAll()
                true
            }
            R.id.theme -> {
                changeTheme()
                true
            }
            R.id.layout -> {
                changeLayout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteNote(id: Int) {
        scope.launch {
            database.deleteNote(id)
            updateTasks()
        }
        Toast.makeText(context, "Задание выполнено!", Toast.LENGTH_SHORT).show()
    }

    private fun deleteAll() {
        if (bindingGrid?.rvNote?.visibility == View.VISIBLE) {
            AlertDialog.Builder(requireContext())
                .setMessage("Вы уверены, что хотите удалить все заметки?")
                .setPositiveButton("Да") { dialog, _ ->
                    scope.launch {
                        database.deleteAll()
                        updateTasks()
                    }
                    Toast.makeText(context, "Все ваши задания удалены", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNegativeButton("Нет") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        } else
            bindingGrid?.let {
                Toast.makeText(context, "Но вы же еще ничего не создали...", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun updateTasks() {
        scope.launch {
            noteList = database.getAll()
            bindingGrid?.apply {
                if (noteList.isEmpty()) {
                    txtEmptyScreen.visibility = View.VISIBLE
                    rvNote.visibility = View.GONE
                } else {
                    txtEmptyScreen.visibility = View.GONE
                    rvNote.visibility = View.VISIBLE
                }
            }
            noteAdapter?.submitList(ArrayList(noteList))
        }

    }

    private fun changeTheme() {
        lightTheme = if (lightTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            context?.setTheme(R.style.Theme_Homework10_Dark)
            false
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            context?.setTheme(R.style.Theme_Homework10_Light)
            true
        }
    }

    private fun changeLayout() {
        val pref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        if (isBindingGrid) {
            bindingGrid?.rvNote?.layoutManager = LinearLayoutManager(context)
            isBindingGrid = false
        } else {
            bindingGrid?.rvNote?.layoutManager = GridLayoutManager(context, 2)
            isBindingGrid = true
        }
        with(pref.edit()) {
            putBoolean("layout", isBindingGrid)
            commit()
        }
    }

    override fun onDestroyView() {
        bindingGrid = null
        bindingLinear = null
        super.onDestroyView()
    }

}

