package com.shubhamjitiya.god.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes_table")
class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val noteTitle: String,
    val noteDescription: String,
    val noteStatus: String,
    val date: String,
    val time: String
)
