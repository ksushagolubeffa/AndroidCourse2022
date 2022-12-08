package com.example.homework9.repository

import com.example.homework9.R
import com.example.homework9.model.Song


object SongRepository {
    val songs = arrayListOf<Song>(
        Song(0, "Снег в океане", R.drawable.snow, R.raw.snow, "Сергей Лазарев", 2019, 0),
        Song(1, "Не влюбляйся", R.drawable.dontlove, R.raw.dontlove, "Mary Gu", 2021, 0),
        Song(2, "О любви", R.drawable.aboutlove, R.raw.aboutlove, "Филипп Киркоров", 2011, 0),
        Song(3, "Мечтатели", R.drawable.dreamm, R.raw.dream, "Дима Билан", 2011, 0),
        Song(4, "Навылет", R.drawable.bullet, R.raw.bullet, "PLC", 2017, 0),
        Song(5, "Не о любви", R.drawable.notaboutlove, R.raw.notaboutlove, "Terry", 2018, 0),
        Song(6, "7 лепесток", R.drawable.flowers, R.raw.flowers, "Антон Токарев", 2021, 0),
    )

    fun getSongById(id: Int): Song {
        return this.songs[id]
    }
}
