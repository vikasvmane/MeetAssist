package com.maverickai.meetassist.feature_create_note.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatGPTResponseModel(
    @field:SerializedName("Summary") val summary: String? = null,

    @field:SerializedName("Domain") val domain: String? = null
) : Parcelable
