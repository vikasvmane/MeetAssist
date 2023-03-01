package com.maverickai.meetassist.feature_list.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id: Int, val title: String, val recordingUrl: String,
    val transcript: String, val summary: String
): Parcelable
