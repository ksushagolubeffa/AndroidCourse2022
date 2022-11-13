package com.example.homework8

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.MemoryFile

import android.provider.MediaStore
import android.provider.MediaStore.ACTION_PICK_IMAGES
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresApi


import com.example.homework8.MainActivity.Companion.CODE
import java.io.FileDescriptor
import java.net.URI



@Suppress("DEPRECATION")
class MyActivityContract : ActivityResultContract<Intent, Bitmap?>() {

    @RequiresApi(33)
    @SuppressLint("QueryPermissionsNeeded")
    override fun createIntent(context: Context, input: Intent): Intent {
        val mutableList = mutableListOf<Intent>()

        val galIntent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        val imageUri: Uri? = galIntent.data;
        if(imageUri != null){
            val bitmap : Bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(
                imageUri.toString()
            ))
            galIntent.putExtra("data", bitmap)
        }
        mutableList.add(galIntent)

        if (input.getIntExtra(CODE, 100) == 101) {
            val camIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).takeIf {
                it.resolveActivity(context.packageManager) != null
            }
            if (camIntent != null) mutableList.add(camIntent)
        }

        val chooserIntent = Intent.createChooser(mutableList.first(), "Choose app to take picture")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, mutableList.toTypedArray())
        chooserIntent.action = Intent.ACTION_CHOOSER
        return chooserIntent
    }

    @Suppress("DEPRECATION")
    override fun parseResult(resultCode: Int, intent: Intent?): Bitmap? = when {
        resultCode != Activity.RESULT_OK -> null
        else -> intent?.getParcelableExtra("data")


    }


}
