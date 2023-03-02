package com.maverickai.meetassist.feature_create_note.data.repository

import com.maverickai.meetassist.feature_list.domain.model.Note
import com.maverickai.meetassist.feature_create_note.domain.CreateNoteRepository
import com.maverickai.meetassist.feature_create_note.domain.GPTDataSource
import com.maverickai.meetassist.feature_create_note.domain.NotesDataSource
import com.maverickai.meetassist.feature_create_note.domain.model.GPTRequest
import com.maverickai.meetassist.feature_create_note.domain.model.GPTResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateNoteRepositoryImpl @Inject constructor(
    private val notesDataSource: NotesDataSource,
    private val gptDataSource: GPTDataSource
) : CreateNoteRepository {
    override suspend fun saveNote(note: Note): Long {
        return notesDataSource.saveNote(note)
    }

    override suspend fun getChatGPTResponse(prompt: String): Flow<GPTResponse> {
        return flow {
            emit(
                gptDataSource.getGPTData(
                    GPTRequest(
                        model = MODEL,
                        temperature = TEMPERATURE,
                        maxTokens = MAX_TOKENS,
                        topP = TOP_P,
                        frequencyPenalty = FREQUENCY_PENALTY,
                        presencePenalty = PRESENCE_PENALTY,
                        prompt = "$PROMPT$prompt"
                    )
                )
            )
        }
    }

    companion object {
        const val MODEL = "text-davinci-003"
        const val TEMPERATURE = 0.7
        const val MAX_TOKENS = 2459
        const val TOP_P = 1
        const val FREQUENCY_PENALTY = 0
        const val PRESENCE_PENALTY = 0
        const val PROMPT = "Get summary from the below text\n"
    }
}