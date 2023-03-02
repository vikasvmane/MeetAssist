package com.maverickai.meetassist.feature_list.domain

import com.maverickai.meetassist.feature_list.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesListRepository {
    fun getNotes(): Flow<List<Note>>
}