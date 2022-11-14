package com.example.homework7


import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework7.databinding.ActivitySecondBinding


class SecondActivity : AppCompatActivity() {

    private var binding: ActivitySecondBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

    }

}