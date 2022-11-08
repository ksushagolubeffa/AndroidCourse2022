package com.example.homework5.model


sealed interface Item  {

    data class Ad(
        val text: String,
        val adress: String,
        val url: String = "",
    ) : Item

    data class Serial(
        val id: Int,
        val name: String,
        val year: String,
        val url: String = "",
        val description: String,
    ): Item
}