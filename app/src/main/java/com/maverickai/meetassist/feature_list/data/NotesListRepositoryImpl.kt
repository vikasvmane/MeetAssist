package com.maverickai.meetassist.feature_list.data

import com.maverickai.meetassist.feature_create_note.domain.NotesDataSource
import com.maverickai.meetassist.feature_list.domain.NotesListRepository
import com.maverickai.meetassist.feature_list.domain.model.Note
import javax.inject.Inject

class NotesListRepositoryImpl @Inject constructor(private val notesDataSource: NotesDataSource) :
    NotesListRepository {
    override suspend fun getNotes(): List<Note> {
        return notesDataSource.getNotes()
    }
}