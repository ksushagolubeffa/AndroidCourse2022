package com.example.homework7


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmapOrNull
import com.example.homework7.FirstActivity.Companion.MY_TIME


class AlarmReceiver:BroadcastReceiver() {

    private lateinit var sound: Uri
    private lateinit var preference: SharedPreferences
    private var ringtone: Ringtone? = null

    @SuppressLint("UnspecifiedImmutableFlag", "UseCompatLoadingForDrawables")
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        preference = context?.getSharedPreferences(FirstActivity.APP_PREFERENCES, Context.MODE_PRIVATE)!!

        intent?.getStringExtra(MY_TIME)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(context, 0, Intent(context, SecondActivity::class.java), PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(context, 0, Intent(context, SecondActivity::class.java), PendingIntent.FLAG_ONE_SHOT)
        }

        val audioAttributes: AudioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
        sound = Uri.parse(
            "android.resource://" + context.packageName + "/" + R.raw.strange
        )

        val picture = BitmapFactory.decodeResource(context.resources, R.drawable.morning)


        val builder = NotificationCompat.Builder(context, "my_alarm")
            .setSmallIcon(R.drawable.alarm)
            .setContentTitle("Гуд морнинг, битч")
            .setContentText("Солнышко мое, вставай")
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(picture)
                .bigLargeIcon(null))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            .setCategory(NotificationCompat.CATEGORY_ALARM)


        context.getDrawable(R.drawable.morning)?.toBitmapOrNull()?.also {
            builder.setLargeIcon(it)
        }



        val notificationManager = NotificationManagerCompat.from(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                "my_alarm",
                "AlarmChanel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lightColor = Color.BLACK
//                setSound(sound, audioAttributes)
                setShowBadge(true)
            }.also {
                notificationManager.createNotificationChannel(it)
            }
        } else {
            builder
                .setLights(Color.BLACK, 100, 100)
//                .setSound(sound)
        }
        playSound(context)
        notificationManager.notify(123, builder.build())


    }

    private fun playSound(context: Context){
        try{
            if(ringtone==null){
                ringtone = RingtoneManager.getRingtone(context, sound)
            }
            if(ringtone?.isPlaying == true){
                ringtone?.stop()
            }else{
                ringtone?.play()
            }

        } catch (e:Exception){
            e.printStackTrace()
            }

    }



}
