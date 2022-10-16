package com.example.homework4

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework4.databinding.FragmentSerialsBinding
import jp.wasabeef.recyclerview.animators.LandingAnimator

class SerialsFragment: Fragment(R.layout.fragment_serials) {
    private var binding: FragmentSerialsBinding?= null

    private var adapter: Serial?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSerialsBinding.bind(view)
        binding?.run{
            val recyclerView = this?.rvSerial
            recyclerView.itemAnimator = LandingAnimator()
            rvSerial.adapter = SerialAdapter(SerialRepository.serials,
                glide = Glide.with(this@SerialsFragment)){
                parentFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                    )
                    .replace(R.id.container, InfoFragment(it.id))
                    .addToBackStack("SerialsFragment")
                    .commit()
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_add -> {
                true
            }
            R.id.action_remove -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }



    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}