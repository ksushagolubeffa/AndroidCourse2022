package com.example.homework3

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.homework3.databinding.FragmentSecondBinding


class SecondFragment : Fragment(R.layout.fragment_second) {
    private var binding: FragmentSecondBinding? = null
    private var score = 0

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondBinding.bind(view)

        arguments?.getString(ARG_NAME)?.also {
            binding?.tv2?.text = it
        }

        arguments?.getInt(ARG_SCORE.toString()).also{
            if (it != null) {
                score = it
                when (it) {
                    in 0..50 -> {
                        view.setBackgroundResource(R.color.first)
                    }
                    in 51..100 -> {
                        view.setBackgroundResource(R.color.second)
                    }
                    else -> {
                        view.setBackgroundResource(R.color.third)
                    }
                }
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){

            override fun handleOnBackPressed() {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, FirstFragment.newInstance(score))
                    .commit()
            }
        })
    }

    companion object {

        private const val ARG_NAME = "name_arg"
        private const val ARG_SCORE = 0

        fun newInstance(text: String, score: Int): SecondFragment {
            val fragment = SecondFragment()
            val bundle = Bundle()
            bundle.putString(ARG_NAME, text)
            bundle.putInt(ARG_SCORE.toString(), score)
            fragment.arguments = bundle
            return fragment
        }
    }
}