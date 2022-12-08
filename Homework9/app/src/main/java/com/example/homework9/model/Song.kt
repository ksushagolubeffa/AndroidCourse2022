package com.example.homework9.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes



data class Song(
    val id: Int,
    val name: String,
    @DrawableRes val cover: Int,
    @RawRes val raw: Int,
    val singer: String,
    val year: Int,
    var colorID: Int
)
