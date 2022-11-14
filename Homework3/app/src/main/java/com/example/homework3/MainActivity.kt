package com.example.homework3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.homework3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        if(savedInstanceState != null) {
            return
        }
        binding?.run {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FirstFragment())
                .commit()
        }


    }

}