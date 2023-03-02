package com.maverickai.meetassist.feature_list.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maverickai.meetassist.feature_list.domain.NotesListRepository
import com.maverickai.meetassist.feature_list.domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(private val notesListRepository: NotesListRepository) :
    ViewModel() {
    private val _notes = MutableLiveData<List<Note>?>()
    val notes: LiveData<List<Note>?> = _notes

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    fun getNotes() {
        viewModelScope.launch {
            val notesList = notesListRepository.getNotes()
            _notes.value = notesList
            Log.d("notes list size ${notesList.size}", notesList.toString())
        }
    }
}