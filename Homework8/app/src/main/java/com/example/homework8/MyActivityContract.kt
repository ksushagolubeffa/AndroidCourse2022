package com.example.homework8

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap


import android.provider.MediaStore
import android.provider.MediaStore.ACTION_PICK_IMAGES
import androidx.activity.result.contract.ActivityResultContract

import androidx.annotation.RequiresApi
import com.example.homework8.MainActivity.Companion.CODE




@Suppress("DEPRECATION")
class MyActivityContract : ActivityResultContract<Intent, Bitmap?>() {

    private var context: Context? = null

    @RequiresApi(33)
    @SuppressLint("QueryPermissionsNeeded")
    override fun createIntent(context: Context, input: Intent): Intent {
        this.context = context
        val mutableList = mutableListOf<Intent>()

        val galIntent = Intent(ACTION_PICK_IMAGES).apply {
            type = "image/*"
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
        else ->
            if(intent?.data != null){
                intent.data?.run {
                MediaStore.Images.Media.getBitmap(
                    context?.contentResolver,
                    this
                )
                }
            }else{
                intent.takeIf { resultCode == Activity.RESULT_OK }?.getParcelableExtra("data")
            }

    }


}
