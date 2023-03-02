package com.maverickai.meetassist.feature_create_note.data.datasource

import com.maverickai.meetassist.common.db.NotesRoomDatabase
import com.maverickai.meetassist.feature_list.domain.model.Note
import com.maverickai.meetassist.feature_create_note.domain.NotesDataSource
import javax.inject.Inject

class LocalNotesDataSourceImpl @Inject constructor(private val notesRoomDatabase: NotesRoomDatabase) :
    NotesDataSource {
    override suspend fun saveNote(note: Note): Long {
        return notesRoomDatabase.notesDao().insertNote(note)
    }

    override suspend fun getNotes(): List<Note> {
        return notesRoomDatabase.notesDao().getAllNotes()
    }
}