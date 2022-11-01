package com.example.homework6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homework6.databinding.FragmentHoroscopeBinding

class HoroscopeFragment: Fragment(R.layout.fragment_horoscope) {
    private var binding: FragmentHoroscopeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val actionBar: androidx.appcompat.app.ActionBar? = (activity as MainActivity?)?.supportActionBar
        actionBar?.setHomeButtonEnabled(false)
        actionBar?.setDisplayHomeAsUpEnabled(false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHoroscopeBinding.bind(view)
        with(binding){
            this?.btnAir?.setOnClickListener {
                findNavController().navigate(R.id.action_horoscopeFragment_to_airFragment)
            }
            this?.btnFire?.setOnClickListener {
                findNavController().navigate(R.id.action_horoscopeFragment_to_fireFragment)
            }
            this?.btnWater?.setOnClickListener {
                findNavController().navigate(R.id.action_horoscopeFragment_to_waterFragment)
            }
            this?.btnGround?.setOnClickListener {
                findNavController().navigate(R.id.action_horoscopeFragment_to_groundFragment)
            }
        }
    }
    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}