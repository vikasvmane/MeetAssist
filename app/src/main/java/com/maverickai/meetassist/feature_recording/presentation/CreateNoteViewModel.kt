package com.maverickai.meetassist.feature_recording.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maverickai.meetassist.feature_recording.domain.CreateNoteRepository
import com.maverickai.meetassist.feature_recording.domain.model.GPTResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateNoteViewModel @Inject constructor(private val createNoteRepository: CreateNoteRepository): ViewModel() {
    private val _gptResponse = MutableLiveData<GPTResponse>()
    val gptResponse: LiveData<GPTResponse> = _gptResponse
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    /**
     * Get states. This can either get it from local db or from remote service based on availability
     */
    fun getChatGPTData(prompt: String) {
        viewModelScope.launch {
            _loading.value = true

            createNoteRepository.getChatGPTResponse(prompt).invoke().flowOn(Dispatchers.IO).catch {
                _loading.value = false
                error.value = it.message
            }.collect {
                _loading.value = false
                error.value = null
            }
        }
    }
}