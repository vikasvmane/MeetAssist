package com.maverickai.meetassist.feature_create_note.domain

import com.maverickai.meetassist.feature_list.domain.model.Note
import com.maverickai.meetassist.feature_create_note.domain.model.GPTResponse
import kotlinx.coroutines.flow.Flow

interface CreateNoteRepository {
    suspend fun saveNote(note: Note): Long
    suspend fun getChatGPTResponse(prompt: String): Flow<GPTResponse>
}