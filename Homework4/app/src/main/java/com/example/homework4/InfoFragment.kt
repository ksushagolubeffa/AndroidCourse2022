package com.example.homework4

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.homework4.databinding.FragmentInfoBinding
import com.example.homework4.databinding.FragmentInformationBinding

class InfoFragment(val idGet:Int):Fragment(R.layout.fragment_information) {
    private var binding: FragmentInformationBinding?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInformationBinding.bind(view)
        with(binding){
            SerialRepository.serials.forEach(){
                if(it.id == idGet){
                    this?.txtName?.text = "Название: " + it.name
                    this?.txtDescr?.text = "Описание: " + it.description
                    this?.txtYear?.text = "Год выпуска: "  + it.year
                    Glide.with(binding!!.root)
                        .load(it.url)
                        .placeholder(R.drawable.netflix)
                        .error(R.drawable.netflix)
                        .into(this!!.iv)
                }
            }
        }
    }

    override fun onDestroyView(){
        super.onDestroyView()
        binding = null
    }
}