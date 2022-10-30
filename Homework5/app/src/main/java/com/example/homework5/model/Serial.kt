package com.example.homework5.model

data class Serial(
    var id: Int,
    val name: String,
    val year: String,
    val url: String = "",
    val description: String,
)