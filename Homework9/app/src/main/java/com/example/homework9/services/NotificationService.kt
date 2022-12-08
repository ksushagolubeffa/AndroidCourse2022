package com.example.homework9.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.homework9.R
import com.example.homework9.activity.MainActivity
import com.example.homework9.repository.SongRepository


class NotificationService(val context: Context) {

    private val notificationId = 1

    private var previousPendingIntent: PendingIntent? = null
    private var resumePendingIntent: PendingIntent? = null
    private var nextPendingIntent: PendingIntent?  = null
    private var stopPendingIntent: PendingIntent? = null
    private var playPendingIntent: PendingIntent? = null
    private var screenPendingIntent: PendingIntent? = null

    private val manager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = context.getString(R.string.channel_description)
            }.also {
                manager.createNotificationChannel(it)
            }

            val previousIntent = Intent(context, MusicService::class.java).apply {
                action = "PREVIOUS"
            }
            val resumeIntent = Intent(context, MusicService::class.java).apply {
                action = "PAUSE"
            }
            val nextIntent = Intent(context, MusicService::class.java).apply {
                action = "NEXT"
            }
            val stopIntent = Intent(context, MusicService::class.java).apply {
                action = "STOP"
            }
            val playIntent = Intent(context,  MusicService::class.java).apply{
                action = "PLAY"
            }

            previousPendingIntent = PendingIntent.getService(context,0, previousIntent,0)
            resumePendingIntent = PendingIntent.getService(context,1, resumeIntent,0)
            nextPendingIntent = PendingIntent.getService(context,2, nextIntent,0)
            stopPendingIntent = PendingIntent.getService(context, 3, stopIntent, 0)
            playPendingIntent = PendingIntent.getService(context, 4, playIntent, 0)
        }
    }

    fun buildNotificationPause(id:Int){
        val track = SongRepository.songs[id]

        screenPendingIntent = createScreenIntent(id)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.music)
            .addAction(R.drawable.previous,"Previous", previousPendingIntent)
            .addAction(R.drawable.pause,"Pause", resumePendingIntent)
            .addAction(R.drawable.next,"Next", nextPendingIntent)
            .addAction(R.drawable.stop, "Stop", stopPendingIntent)
            .setContentTitle(track.name)
            .setContentText(track.singer)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle())
            .setLargeIcon(BitmapFactory.decodeResource(context.resources,track.cover))
            .setContentIntent(screenPendingIntent)

        manager.notify(notificationId, builder.build())
    }

    fun buildNotificationPlay(id:Int){
        val track = SongRepository.songs[id]

        screenPendingIntent = createScreenIntent(id)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.music)
            .addAction(R.drawable.previous,"Previous", previousPendingIntent)
            .addAction(R.drawable.play, "Play", playPendingIntent)
            .addAction(R.drawable.next,"Next", nextPendingIntent)
            .addAction(R.drawable.stop, "Stop", stopPendingIntent)
            .setContentTitle(track.name)
            .setContentText(track.singer)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle())
            .setLargeIcon(BitmapFactory.decodeResource(context.resources,track.cover))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(screenPendingIntent)

        manager.notify(notificationId, builder.build())
    }

    private fun createScreenIntent(id:Int): PendingIntent {
        val bundle = Bundle()
        bundle.putInt("id", id)
        screenPendingIntent = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.main_graph)
            .setDestination(R.id.infoFragment)
            .setArguments(bundle)
            .createPendingIntent()
        return screenPendingIntent as PendingIntent
    }

    companion object{
        private const val CHANNEL_ID = "music_channel"
    }
}