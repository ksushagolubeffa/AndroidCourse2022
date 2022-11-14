package com.example.homework8

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.example.homework8.databinding.ActivityMainBinding
import java.io.File
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? =null
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also{
            setContentView(it.root)
        }

        val contentLauncher = registerForActivityResult(MyActivityContract()) {
            res -> if(res!=null) binding!!.iv.setImageBitmap(res)
            else binding!!.iv.setImageURI(imageUri)
        }

        val intentMain = Intent()

        val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                    intentMain.putExtra(CODE, 101)
                    contentLauncher.launch(intentMain)
                    Toast.makeText(applicationContext, "Вы дали приложению доступ к камере", Toast.LENGTH_SHORT).show()
            }
            else {
                    intentMain.putExtra(CODE, 100)
                    contentLauncher.launch(intentMain)
                    Toast.makeText(applicationContext, "Вы не дали приложению доступ к камере", Toast.LENGTH_SHORT).show()
            }
        }

        binding?.btn?.setOnClickListener {

            launcher.launch(android.Manifest.permission.CAMERA)
        }

    }

    companion object{
        const val CODE = "code"
    }

}