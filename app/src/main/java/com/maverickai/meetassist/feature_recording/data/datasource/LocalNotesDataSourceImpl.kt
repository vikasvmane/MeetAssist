package com.maverickai.meetassist.feature_recording.data.datasource

import com.maverickai.meetassist.common.db.NotesRoomDatabase
import com.maverickai.meetassist.feature_list.domain.model.Note
import com.maverickai.meetassist.feature_recording.domain.NotesDataSource
import javax.inject.Inject

class LocalNotesDataSourceImpl @Inject constructor(private val notesRoomDatabase: NotesRoomDatabase) :
    NotesDataSource {
    override suspend fun saveNote(note: Note) {
        notesRoomDatabase.notesDao().insertNote(note)
    }

    override suspend fun getNotes(): List<Note> {
        return notesRoomDatabase.notesDao().getAllNotes()
    }
}