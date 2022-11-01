package com.example.homework3

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.homework3.databinding.FragmentDialogBinding

class AddDialogFragment(val counterValue: Int, val onButtonClicked: (Int) -> Unit) : DialogFragment(R.layout.fragment_dialog) {

    private var binding: FragmentDialogBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var editTextValue = 0

        binding = FragmentDialogBinding.bind(view)
        with(binding) {
            this?.good?.setOnClickListener {
                if(checkValue()) {
                    onButtonClicked(counterValue + this?.editTextName?.text.toString().toInt())
                }


            }
            this?.bad?.setOnClickListener {
                dismiss()
            }
            this?.neutral?.setOnClickListener {
//                if (this?.editTextName?.text.isNullOrBlank()) {
//                    editTextValue = Integer.valueOf(binding?.editTextName?.text.toString())
//                }
                if(checkValue()&& (counterValue - this?.editTextName?.text.toString().toInt())>0){
                    onButtonClicked(counterValue - this?.editTextName?.text.toString().toInt())
                } else{
                    onButtonClicked(0)
                }
            }
        }
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
            return true
        }
        return false
    }

//    private fun initClickListeners(){
//        var editTextValue = 0
//        Toast.makeText(context,counterValue.toString(), Toast.LENGTH_LONG ).show()
//
//        with(binding) {
//            positiveButton.setOnClickListener {
//                if (!binding.etCounter.text.isNullOrBlank()) {
//                    editTextValue = Integer.valueOf(binding.etCounter.text.toString())
//                }
//                if(checkValue()) {
//                    onButtonClicked(counterValue + editTextValue)
//                }
//
//            }
//            neutralButton.setOnClickListener {
//                dismiss()
//            }
//            negativeButton.setOnClickListener {
//                if (!binding.etCounter.text.isNullOrBlank()) {
//                    editTextValue = Integer.valueOf(binding.etCounter.text.toString())
//                }
//                if(checkValue()) onButtonClicked(counterValue - editTextValue)
//
//            }
//        }
    }
