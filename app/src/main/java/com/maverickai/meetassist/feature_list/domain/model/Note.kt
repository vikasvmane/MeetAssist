package com.maverickai.meetassist.feature_list.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)val id: Int, val title: String, val recordingUrl: String,
    val transcript: String, val summary: String
): Parcelable
