package com.example.homework10.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.homework10.R
import com.example.homework10.database.NoteRepository
import com.example.homework10.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var database: NoteRepository
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        database = NoteRepository(this)
        (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment)
            .navController


    }
}