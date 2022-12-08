package com.example.homework9.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.homework9.R
import com.example.homework9.databinding.ActivityMainBinding
import com.example.homework9.fragments.SongsFragment
import com.example.homework9.model.Song
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        controller = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment)
            .navController

    }

}