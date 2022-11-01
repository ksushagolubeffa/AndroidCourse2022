package com.example.homework6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homework6.databinding.FragmentHoroscopeContainerBinding

class HoroscopeContainerFragment: Fragment(R.layout.fragment_horoscope_container) {
    private var binding: FragmentHoroscopeContainerBinding? = null

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
        binding = FragmentHoroscopeContainerBinding.bind(view)
        binding?.run{
            if(requireActivity() is MainActivity)
            findNavController().navigate(R.id.action_horoscopeContainerFragment_to_horoscopeFragment)
        }
    }
    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}