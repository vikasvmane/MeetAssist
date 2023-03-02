package com.maverickai.meetassist.feature_create_note.domain

import com.maverickai.meetassist.feature_create_note.domain.model.GPTRequest
import com.maverickai.meetassist.feature_create_note.domain.model.GPTResponse

interface GPTDataSource {
    suspend fun getGPTData(gptRequest: GPTRequest): GPTResponse
}