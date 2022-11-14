package com.example.homework4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework4.databinding.ActivityMainBinding
import jp.wasabeef.recyclerview.animators.LandingAnimator

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