package com.maverickai.meetassist.feature_list.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int? = null, val title: String, val recordingUrl: String? = null,
    val transcript: String, val summary: String
) : Parcelable
