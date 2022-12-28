package com.example.homework10.database

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend fun add(note: Note)

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun delete(id: Int)

    @Update
    suspend fun update(note: Note)

    @Query("DELETE FROM note")
    suspend fun deleteAll()

    @Query("SELECT * from note WHERE id = :id")
    suspend fun getOne(id: Int): Note?

    @Query("SELECT * from note")
    suspend fun getAll(): List<Note>
}