package com.example.homework9.fragments

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.*
import android.view.View
import jp.wasabeef.recyclerview.animators.LandingAnimator
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homework9.R
import com.example.homework9.repository.SongRepository
import com.example.homework9.databinding.FragmentSongsBinding
import com.example.homework9.model.Song
import com.example.homework9.rv.SongAdapter

class SongsFragment: Fragment(R.layout.fragment_songs) {

    private var binding: FragmentSongsBinding?= null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSongsBinding.bind(view)
        binding?.run {
            val recyclerView = this.rvSong
            recyclerView.itemAnimator = LandingAnimator()
            rvSong.adapter = SongAdapter(
                SongRepository.songs,
                onItemClick = ::onSongItemClick)

        }
    }

    private fun onSongItemClick(song: Song){

        if(song.colorID ==0){
            for(s in SongRepository.songs){
                if(s.colorID == 1){
                    s.colorID = 0
                }
            }
        }
        song.colorID = 1

        val bundle = Bundle().apply {
            putInt("id", song.id)
        }
        findNavController().navigate(R.id.action_songsFragment_to_infoFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}

