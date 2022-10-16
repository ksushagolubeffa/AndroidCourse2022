package com.example.homework3

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.homework3.databinding.FragmentDialogBinding


class NewDialogFragment : DialogFragment(R.layout.fragment_dialog) {

    private var binding: FragmentDialogBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDialogBinding.bind(view)

        val bundle = Bundle()
        val res = binding?.editTextName?.text.toString()
        if(checkValue()){
            bundle.putString("INPUT_SCORE", res)
        }
        arguments = bundle

        }

    private fun isNumeric(toCheck: String): Boolean {
        return toCheck.all { char -> char.isDigit() }
    }

    private fun checkValue() : Boolean{
        if (!binding?.editTextName?.text.isNullOrBlank()) {
            if(!isNumeric(binding?.editTextName?.text.toString())){
                binding?.textInputLayout?.error = "Неверный формат данных"
                return false
            } else{
                val editTextValue = Integer.valueOf(binding?.editTextName?.text.toString())
                if (editTextValue !in 0..100) {
                    binding?.textInputLayout?.error = "Неверный формат данных"
                    return false
                } else {
                    binding?.textInputLayout?.isErrorEnabled = false
                }
            }
        }
        return true
    }
    }


