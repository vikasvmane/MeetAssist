package com.maverickai.meetassist.feature_recording.domain

import com.maverickai.meetassist.feature_list.domain.model.Note

interface NotesDataSource {
    suspend fun saveNote(note: Note)
    suspend fun getNotes(): List<Note>
}