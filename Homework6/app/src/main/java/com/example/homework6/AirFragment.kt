package com.example.homework6

import android.annotation.SuppressLint
import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homework6.databinding.FragmentAirBinding


class AirFragment: Fragment(R.layout.fragment_air) {
    private var binding: FragmentAirBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val actionBar: androidx.appcompat.app.ActionBar? = (activity as MainActivity?)?.supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAirBinding.bind(view)
//        binding?.run {
//            setSupportActionBar(toolbar)
//            toolbar.setTitleTextColor(R.color.black)
//            supportActionBar?.setDisplayHomeAsUpEnabled(false)
//        }

    }
    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}