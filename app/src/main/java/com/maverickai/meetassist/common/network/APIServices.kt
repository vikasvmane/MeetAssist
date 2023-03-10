package com.maverickai.meetassist.common.network

import com.maverickai.meetassist.feature_recording.domain.model.GPTRequest
import com.maverickai.meetassist.feature_recording.domain.model.GPTResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface APIServices {
    @POST("/v1/completions")
    suspend fun getGPTData(@Body gptRequest: GPTRequest): GPTResponse
}