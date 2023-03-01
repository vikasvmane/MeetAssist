package com.maverickai.meetassist.feature_recording.domain

import com.maverickai.meetassist.feature_list.domain.model.Note
import com.maverickai.meetassist.feature_recording.domain.model.GPTResponse
import kotlinx.coroutines.flow.Flow

interface CreateNoteRepository {
    suspend fun saveNote(note: Note)
    suspend fun getChatGPTResponse(prompt: String): Flow<GPTResponse>
}