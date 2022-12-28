package com.example.homework10.database

import android.content.Context
import androidx.room.Room

class NoteRepository(context: Context) {
    private val db by lazy {
        Room.databaseBuilder(context, NoteDatabase::class.java, DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
    }
    private val noteDao by lazy {
        db.getNoteDao()
    }

    suspend fun deleteNote(id: Int) {
        noteDao.delete(id)
    }

    suspend fun deleteAll() {
        noteDao.deleteAll()
    }

    suspend fun addNote(note: Note) {
        noteDao.add(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.update(note)
    }

    suspend fun getAll(): List<Note> {
        return noteDao.getAll()
    }

    suspend fun findNoteById(id: Int): Note? {
        return noteDao.getOne(id)
    }

    companion object {
        private const val DATABASE_NAME = "notes.db"
    }
}