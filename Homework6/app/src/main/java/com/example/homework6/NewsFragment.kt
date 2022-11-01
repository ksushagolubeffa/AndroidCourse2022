package com.example.homework6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homework6.databinding.FragmentNewsBinding

class NewsFragment:Fragment(R.layout.fragment_news) {
    private var binding: FragmentNewsBinding? = null

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
        binding = FragmentNewsBinding.bind(view)

    }
    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}