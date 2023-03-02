package com.maverickai.meetassist.feature_list.data

import com.maverickai.meetassist.feature_list.domain.NotesListRepository
import com.maverickai.meetassist.feature_list.domain.model.Note
import com.maverickai.meetassist.feature_recording.domain.NotesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotesListRepositoryImpl @Inject constructor(private val notesDataSource: NotesDataSource) :
    NotesListRepository {
    override fun getNotes(): Flow<List<Note>> {
        return flow { emit(notesDataSource.getNotes()) }
    }
}