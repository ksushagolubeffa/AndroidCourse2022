package com.example.homework9.services

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.homework9.model.Song
import com.example.homework9.repository.SongRepository


class MusicService : Service() {
    private lateinit var mediaPlayer : MediaPlayer
    var currentSongId: Int? = null
    lateinit var songs: ArrayList<Song>
    private lateinit var musicBinder: MusicBinder
    private lateinit var notificationService: NotificationService

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onBind(intent: Intent?): IBinder = musicBinder

    override fun onCreate() {
        super.onCreate()
        currentSongId = 0
        mediaPlayer = MediaPlayer()
        musicBinder = MusicBinder()
        songs = SongRepository.songs
        notificationService = NotificationService(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "PREVIOUS" -> {
                playPrevious()
            }
            "PAUSE" -> {
                if (mediaPlayer.isPlaying) pauseSong()
            }
            "PLAY" -> {
                if (!mediaPlayer.isPlaying) playSong()
            }
            "NEXT" -> {
                playNext()
            }
            "STOP" -> {
                stopSong()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun getDuration(): Int {
        return mediaPlayer.duration
    }

    fun getCurrentPosition(): Int{
        return mediaPlayer.currentPosition
    }

    fun playPrevious() {
        currentSongId?.let {
            currentSongId = if (it == 0) {
                songs.size - 1
            } else {
                it - 1
            }
            setSong(currentSongId ?: 0)
            playSong()
        }
    }

    fun playNext() {
        currentSongId?.let {
            currentSongId = if (it == songs.size - 1) {
                0
            } else {
                it + 1
            }
            setSong(currentSongId ?: 0)
            playSong()
        }
    }

    fun pauseSong() {
        mediaPlayer.pause()
        currentSongId?.let {
            notificationService.buildNotificationPlay(it)
        }
    }

    fun playSong() {
        mediaPlayer.start()
        currentSongId?.let {
            notificationService.buildNotificationPause(it)
        }
    }

    fun stopSong() {
        mediaPlayer.stop()
        setSong(currentSongId ?: 0)
        currentSongId?.let {
            notificationService.buildNotificationPlay(it)
        }
    }

    fun setSong(id: Int) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer = MediaPlayer.create(applicationContext, songs[id].raw)
        currentSongId = id
    }

    fun isMusicPlaying(): Boolean{
        return mediaPlayer.isPlaying
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}