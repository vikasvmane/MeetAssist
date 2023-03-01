package com.maverickai.meetassist.feature_list.presentation

import com.maverickai.meetassist.feature_list.domain.model.Note

interface OnNotesClickListener {
    fun onNoteClicked(note: Note)
}