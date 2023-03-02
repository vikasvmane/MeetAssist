package com.maverickai.meetassist.feature_create_note.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maverickai.meetassist.feature_list.domain.model.Note
import com.maverickai.meetassist.feature_create_note.domain.CreateNoteRepository
import com.maverickai.meetassist.feature_create_note.domain.model.GPTResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(private val createNoteRepository: CreateNoteRepository) :
    ViewModel() {
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
            createNoteRepository.getChatGPTResponse(prompt).flowOn(Dispatchers.IO).catch {
                _loading.value = false
                _error.value = it.message
            }.collect {
                _gptResponse.value = it
                _loading.value = false
                _error.value = null
            }
        }
    }

    fun saveNote(note: Note) {
        viewModelScope.launch {
            val saveNotesResponse = createNoteRepository.saveNote(note)
            Log.d("Save Notes", saveNotesResponse.toString())
            if (saveNotesResponse != 1L) {
                _error.value = "Error in saving notes"
            }
        }
    }
}