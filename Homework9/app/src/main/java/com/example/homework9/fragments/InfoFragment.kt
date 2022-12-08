package com.example.homework9.fragments

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homework9.services.MusicService
import com.example.homework9.R
import com.example.homework9.repository.SongRepository
import com.example.homework9.databinding.FragmentInfoBinding
import java.text.SimpleDateFormat
import java.util.*

class InfoFragment: Fragment(R.layout.fragment_info) {
    private var binding: FragmentInfoBinding?= null
    private var musicService: MusicService? = null
    private var clickCounter: Int = 0
    private var shouldUpdateSeekbar = true

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            musicService = (service as? MusicService.MusicBinder)?.getService()
            if(musicService != null){
                initView()
            }
        }

        override fun onServiceDisconnected(className: ComponentName) {
            musicService = null
        }
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this.context, MusicService::class.java)
        activity?.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        musicService?.currentSongId?.let {
            updateView(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)

        initView()
    }

    private fun initView() {
        with(binding){
            SongRepository.songs.forEach(){

                if(it.id == arguments?.getInt("id")) {
                    this?.txtName?.text = it.name
                    this?.txtSinger?.text = it.singer
                    this?.txtYear?.text = it.year.toString()
                    this?.iv?.setImageResource(it.cover)
                    initMusic(it.id)
                }
            }
        }
    }

    private fun initMusic(id: Int) {
        musicService?.setSong(id)
        musicService?.playSong()

        with(binding){
            this?.seekbar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if(fromUser) {
                        setCurPlayerTimeToTextView(progress.toLong())
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekBar?.let {
                    }
                }

            })
            this?.previousBtn?.setOnClickListener {
                musicService?.playPrevious()
                updateView(musicService?.currentSongId?:0)
            }
            this?.nextBtn?.setOnClickListener {
                musicService?.playNext()
                updateView(musicService?.currentSongId?:0)
            }
            this?.pauseBtn?.setOnClickListener {
                if(clickCounter%2==0){
                    musicService?.pauseSong()
                    showPlaySign()
                }
                else{
                    musicService?.playSong()
                    showPauseSign()
                }
                clickCounter++
            }
            this?.stopBtn?.setOnClickListener {
                musicService?.stopSong()
                showPlaySign()
                clickCounter = 1
            }
//            initialiseSeekBar()
//            binding.curPlayerPosition.observe(viewLifecycleOwner) {
//                if(shouldUpdateSeekbar) {
//                    seekBar.progress = it.toInt()
//                    setCurPlayerTimeToTextView(it)
//                }
//            }
//            songViewModel.curSongDuration.observe(viewLifecycleOwner) {
//                seekBar.max = it.toInt()
//                val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
//                tvSongDuration.text = dateFormat.format(it)
//            }
        }
    }

    private fun updateView(id:Int){
        val currentSong = SongRepository.getSongById(id)

        with(binding) {
            this?.txtName?.text = currentSong.name
            this?.txtSinger?.text = currentSong.singer
            this?.txtSinger?.text = currentSong.singer
            this?.iv?.setImageResource(currentSong.cover)
            this?.playBtn?.setOnClickListener {
                musicService?.playSong()
                showPauseSign()
            }
        }

        if(musicService?.isMusicPlaying() == true){
            showPauseSign()

        } else {
            showPlaySign()
        }
    }

    private fun showPauseSign(){
        with(binding){
            this?.pauseBtn?.setBackgroundResource(R.drawable.pause)
        }
    }

    private fun showPlaySign(){
        with(binding){
            this?.pauseBtn?.setBackgroundResource(R.drawable.play)
        }
    }


    override fun onDestroyView(){
        super.onDestroyView()
        binding = null
    }

    private fun setCurPlayerTimeToTextView(ms: Long) {
        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        binding?.elapsedTimeLabel?.text = dateFormat.format(ms)
    }

//    private fun initialiseSeekBar(){
//        binding?.seekbar?.max = musicService?.getDuration()!!
//        val handler = Handler()
//        handler.postDelayed(object: Runnable{
//            override fun run() {
//                try {
//                binding?.seekbar?.progress = musicService?.getCurrentPosition()!!
//                handler.postDelayed(this, 1000)
//                } catch (e: Exception){
//                    binding?.seekbar?.progress = 0
//                }
//            }
//
//        }, 0)
//    }


}

