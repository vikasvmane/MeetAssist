package com.maverickai.meetassist.feature_create_note.domain

import com.maverickai.meetassist.feature_list.domain.model.Note

interface NotesDataSource {
    suspend fun saveNote(note: Note): Long
    suspend fun getNotes(): List<Note>
}