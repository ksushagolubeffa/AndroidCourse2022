package com.example.homework3


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.homework3.databinding.FragmentDialogBinding


open class MyDialogFragment : DialogFragment() {
//    private var binding: FragmentDialogBinding? = null
//
//    @SuppressLint("SetTextI18n")
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val view =
//            LayoutInflater.from(requireContext()).inflate(R.layout.fragment_dialog, null, false)
//        binding = FragmentDialogBinding.bind(view)
//        val et: Editable? = binding?.editTextName?.text
//        var sc = 0
//        arguments?.getInt("SCORE")?.also {
//            sc = it
//        }
//
//        return AlertDialog.Builder(requireContext())
//            .setView(view)
//            .setPositiveButton("PLUS") { dialog, _ ->
//                dialog.dismiss()
//
//            }
//            .setNegativeButton("CLOSE") { dialog, _ ->
//                dialog.dismiss()
//            }
//            .setNeutralButton("MINUS") { dialog, _ ->
//                dialog.dismiss()
//            }
//            .create()

    }

//    fun checkValue() : Boolean{
//        if (!binding?.editTextName?.text.isNullOrBlank()) {
//            val editTextValue = Integer.valueOf(binding?.editTextName?.text.toString())
//            if (editTextValue !in 0..100) {
//                binding?.textInputLayout?.error = "Неверный формат данных"
//                return false
//            } else {
//                binding?.textInputLayout?.isErrorEnabled = false
//            }
//        }
//        return true
//    }
//
//    fun getScore():MyDialogFragment{
//        val fragment = MyDialogFragment()
//        val bundle = Bundle()
//        val res = binding?.editTextName?.text.toString()
//        bundle.putString("INPUT_SCORE", res)
//        fragment.arguments = bundle
//        return fragment
//    }
