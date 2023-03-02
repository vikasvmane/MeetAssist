package com.maverickai.meetassist.feature_list.domain

import com.maverickai.meetassist.feature_list.domain.model.Note

interface NotesListRepository {
    suspend fun getNotes(): List<Note>
}