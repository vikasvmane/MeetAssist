package com.maverickai.meetassist.feature_recording.domain

import com.maverickai.meetassist.feature_recording.domain.model.GPTRequest
import com.maverickai.meetassist.feature_recording.domain.model.GPTResponse

interface GPTDataSource {
    suspend fun getGPTData(gptRequest: GPTRequest): GPTResponse
}