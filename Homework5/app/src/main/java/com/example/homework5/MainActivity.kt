package com.example.homework5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homework5.databinding.ActivityMainBinding
import com.example.homework5.fragment.SerialsFragment
import com.example.homework5.model.Serial

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    private var adapter: Serial?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        if (savedInstanceState != null) {
            return
        }
        binding?.run {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SerialsFragment())
                .commit()
        }


    }
}