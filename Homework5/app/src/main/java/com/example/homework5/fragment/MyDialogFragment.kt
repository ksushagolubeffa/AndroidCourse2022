package com.example.homework5.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.homework5.R
import com.example.homework5.databinding.FragmentDialogBinding
import com.example.homework5.model.Item
import com.example.homework5.model.Serial
import com.example.homework5.model.SerialRepository
import java.lang.Integer.parseInt

class MyDialogFragment(val onAddButtonClicked: (position: Int, Item.Serial) -> Unit)
    : DialogFragment(R.layout.fragment_dialog) {
    private var binding: FragmentDialogBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDialogBinding.bind(view)

        with(binding){
            this?.ok?.setOnClickListener{
                if (checkValue() == 1){
                    if(parseInt(this.etPos.text.toString()) > SerialRepository.mainItems.size){
                        onAddButtonClicked(SerialRepository.mainItems.size,
                            Item.Serial(SerialRepository.serials.size + 1,
                                this.etTitle.text.toString(),
                                this.etDesc.text.toString(), "", ""))
                        SerialRepository.serials.add(Serial(SerialRepository.serials.size + 1,
                            this.etTitle.text.toString(),this.etDesc.text.toString(), "", ""))

                    } else{
                    onAddButtonClicked(parseInt(this.etPos.text.toString()),
                        Item.Serial(SerialRepository.serials.size + 1,
                            this.etTitle.text.toString(),
                            this.etDesc.text.toString(), "", ""))
                    SerialRepository.serials.add(Serial(SerialRepository.serials.size + 1,
                        this.etTitle.text.toString(),this.etDesc.text.toString(), "", ""))}
                    dismiss()
                } else if(checkValue() == 2){
                    onAddButtonClicked(SerialRepository.mainItems.size,
                        Item.Serial(SerialRepository.serials.size + 1,
                            this.etTitle.text.toString(),
                            this.etDesc.text.toString(), "", ""))
                    SerialRepository.serials.add(Serial(SerialRepository.serials.size + 1,
                        this.etTitle.text.toString(),this.etDesc.text.toString(), "", ""))
                    dismiss()
                }
            }
            this?.close?.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val metrics: DisplayMetrics = resources.displayMetrics
        val width: Int = metrics.widthPixels
        dialog?.window?.setLayout(
            width,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun checkValue() : Int{
        if (binding?.etTitle?.text.isNullOrBlank()) {
                binding?.tilTitle?.error = "Введите название сериала"
                return 0
            } else if(binding?.etDesc?.text.isNullOrBlank()){
                binding?.tilDesc?.error = "Ввведите год выхода сериала"
                return 0
            } else if(binding?.etPos?.text.isNullOrBlank()){
                return 2
            }
            else {
                binding?.tilTitle?.isErrorEnabled = false
                binding?.tilPos?.isErrorEnabled = false
                binding?.tilDesc?.isErrorEnabled = false
                return 1
            }
    }


}