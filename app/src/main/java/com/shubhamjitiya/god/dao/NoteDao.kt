package com.shubhamjitiya.god.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shubhamjitiya.god.entities.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Update
    fun updateNote(note: Note)
}