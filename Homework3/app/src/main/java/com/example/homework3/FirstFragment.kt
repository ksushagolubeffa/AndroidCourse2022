package com.example.homework3

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.homework3.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first){
    private var binding: FragmentFirstBinding? = null

    private var score = 0

    private fun getScore(): Int? {
        return arguments?.run {
            getInt(ARG_SCORE)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)

        with(binding){
            this?.bt1?.setOnClickListener {
                parentFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                    )
                    .replace(R.id.container, SecondFragment.newInstance(tv1.text.toString(), score))
                    .addToBackStack("FirstFragment")
                    .commit()
            }
            this?.bt2?.setOnClickListener{
                score += 1
                tv1.text = "Counter value: $score"
            }
            this?.bt3?.setOnClickListener{

                AddDialogFragment(score) {
                    score = it
                    tv1.text = "Counter value: $score"
                }.show(parentFragmentManager, "YES")
//                showDialog(
//                    positiveAction = {
//                        arguments?.getString("INPUT_SCORE")?.also{
//                            Log.d("da", it)
//                            score += Integer.valueOf(data)
//                            this.tv1.text = "Counter value: $score"
//                        }
//                },
//                    neutralAction = {
//                        arguments?.getString("INPUT_SCORE")?.also{
//                            if(score - Integer.valueOf(it) < 0){
//                                score = 0
//                            } else{
//                                score -= Integer.valueOf(it)
//                            }
//                            this.tv1.text = "Counter value: $score"
//                        }
//                    }
//                )

            }
            getScore()?.let {
                score = it
                this?.tv1?.text = "Counter value: $it"
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("score",score)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let{
            score = it.getInt("score")
            binding?.tv1?.text = "Counter value: $score"
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {

        const val ARG_SCORE = "SCORE"

        fun newInstance(score: Int): FirstFragment {
            val fragment = FirstFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SCORE, score)
            fragment.arguments = bundle
            return fragment
        }
    }
}