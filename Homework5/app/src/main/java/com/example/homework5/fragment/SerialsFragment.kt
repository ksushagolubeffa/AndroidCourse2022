package com.example.homework5.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.homework5.R
import com.example.homework5.SerialsListAdapter
import com.example.homework5.databinding.FragmentSerialsBinding
import com.example.homework5.model.Item
import com.example.homework5.model.SerialRepository
import jp.wasabeef.recyclerview.animators.LandingAnimator

class SerialsFragment: Fragment(R.layout.fragment_serials) {
    private var binding: FragmentSerialsBinding?= null

    private var listAdapter: SerialsListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSerialsBinding.bind(view)
        binding?.run{
            val recyclerView = this.rvSerial
            recyclerView.itemAnimator = LandingAnimator()
            listAdapter = SerialsListAdapter(SerialRepository.mainItems,
                glide = Glide.with(this@SerialsFragment),
                onItemClick = {
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
            },
                onDeleteClick = ::onDeleteClick)


            rvSerial.adapter = listAdapter
            listAdapter?.submitList(SerialRepository.mainItems)


            binding?.btnAdd?.setOnClickListener {
                MyDialogFragment(onAddButtonClicked =
                ::addItem).show(parentFragmentManager, "YES")
            }
        }
    }

    private fun addItem(position: Int, item: Item.Serial) {
        SerialRepository.addItem(position, item)
        listAdapter?.submitList(SerialRepository.mainItems)
    }

     private fun onDeleteClick(position: Int) {
        SerialRepository.deleteItem(position)
        listAdapter?.submitList(SerialRepository.mainItems)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}