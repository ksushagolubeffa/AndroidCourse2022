package com.example.homework7

import android.annotation.SuppressLint
import android.app.*
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.homework7.databinding.ActivityFirstBinding
import java.text.SimpleDateFormat
import java.util.*


class FirstActivity : AppCompatActivity() {

    private var binding: ActivityFirstBinding? = null
    private lateinit var calendar: Calendar
    private val context = this@FirstActivity
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var preference: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        createNotificationChannel()
        preference = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        with(binding){
            this?.tvDate?.let { getDate(it, context) }
            this?.let { getTime(it.tvTime, context) }
            this?.btnStart?.setOnClickListener {
                setAlarm(calendar.timeInMillis)
                Toast.makeText(context, "Будильник установлен", Toast.LENGTH_SHORT).show()
            }
            this?.btnStop?.setOnClickListener{
                cancelAlarm()
            }
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate(textView: TextView, context: Context){
        calendar = Calendar.getInstance()
        val dateSetListener = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            textView.text = SimpleDateFormat("dd.MM.yyyy").format(calendar.time)

        }

        textView.setOnClickListener {
            DatePickerDialog(context, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTime(textView: TextView, context: Context){
        calendar = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            textView.text = SimpleDateFormat("HH:mm").format(calendar.time)
        }

        textView.setOnClickListener {

            TimePickerDialog(context, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    private fun cancelAlarm() {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,AlarmReceiver::class.java)
        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        }
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this,"Будильник отменен",Toast.LENGTH_SHORT).show()
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    private fun setAlarm(time: Long) {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,AlarmReceiver::class.java)
        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP, time,
            pendingIntent
        )
    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name: CharSequence = "AlarmChanel"
            val description = "Channel For Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("my_alarm", name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

//    override fun onPause() {
//        super.onPause()
//        val editor = preference.edit()
//        editor.putInt(MY_TIME, calendar.timeInMillis.toInt()).apply()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if(preference.contains(MY_TIME)){
//            val time = preference.getInt(MY_TIME, -1).toLong()
//            setAlarm(time)
//        }
//    }

    companion object{
        const val APP_PREFERENCES = "settings"
        const val MY_TIME = "alarm time"
        const val RINGTONE = "music"
    }

}