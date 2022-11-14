package com.example.homework6

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.homework6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        val controller =
            (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment)
                .navController

        binding?.run {
            bnvMain.setupWithNavController(controller)
            bnvMain.selectedItemId = R.id.horoscopeFragment

            setSupportActionBar(toolbar)
//            toolbar.setTitleTextColor(R.color.black)
            supportActionBar?.setHomeButtonEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}