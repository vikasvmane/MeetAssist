package com.maverickai.meetassist.feature_recording.domain.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GPTRequest(

	@field:SerializedName("top_p")
	val topP: Int? = null,

	@field:SerializedName("frequency_penalty")
	val frequencyPenalty: Int? = null,

	@field:SerializedName("max_tokens")
	val maxTokens: Int? = null,

	@field:SerializedName("presence_penalty")
	val presencePenalty: Int? = null,

	@field:SerializedName("temperature")
	val temperature: Double? = null,

	@field:SerializedName("model")
	val model: String? = null,

	@field:SerializedName("prompt")
	val prompt: String? = null
) : Parcelable
