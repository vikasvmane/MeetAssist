package com.maverickai.meetassist.feature_list.domain.model

data class Note(
    val id: Int, val title: String, val recordingUrl: String,
    val transcript: String, val summary: String
)
