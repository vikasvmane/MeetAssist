package com.maverickai.meetassist.feature_create_note.domain.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GPTResponse(

	@field:SerializedName("created")
	val created: Int? = null,

	@field:SerializedName("usage")
	val usage: Usage? = null,

	@field:SerializedName("model")
	val model: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("choices")
	val choices: List<ChoicesItem?>? = null,

	@field:SerializedName("object")
	val _object: String? = null
) : Parcelable

@Parcelize
data class Usage(

	@field:SerializedName("completion_tokens")
	val completionTokens: Int? = null,

	@field:SerializedName("prompt_tokens")
	val promptTokens: Int? = null,

	@field:SerializedName("total_tokens")
	val totalTokens: Int? = null
) : Parcelable

@Parcelize
data class ChoicesItem(

	@field:SerializedName("finish_reason")
	val finishReason: String? = null,

	@field:SerializedName("index")
	val index: Int? = null,

	@field:SerializedName("text")
	val text: String? = null
) : Parcelable
