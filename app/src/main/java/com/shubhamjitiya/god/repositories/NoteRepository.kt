package com.example.notes.repositories

import androidx.lifecycle.LiveData
import com.shubhamjitiya.god.dao.NoteDao
import com.shubhamjitiya.god.entities.Note

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    fun updateData(note: Note) {
        noteDao.updateNote(note)
    }

}