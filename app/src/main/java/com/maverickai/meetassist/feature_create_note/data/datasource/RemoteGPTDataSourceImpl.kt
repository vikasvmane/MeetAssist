package com.maverickai.meetassist.feature_create_note.data.datasource

import com.maverickai.meetassist.common.network.APIServices
import com.maverickai.meetassist.feature_create_note.domain.GPTDataSource
import com.maverickai.meetassist.feature_create_note.domain.model.GPTRequest
import com.maverickai.meetassist.feature_create_note.domain.model.GPTResponse
import javax.inject.Inject

class RemoteGPTDataSourceImpl @Inject constructor(private val apiServices: APIServices) :
    GPTDataSource {
    override suspend fun getGPTData(gptRequest: GPTRequest): GPTResponse {
        return apiServices.getGPTData(gptRequest)
    }
}