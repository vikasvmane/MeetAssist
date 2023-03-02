package com.maverickai.meetassist.feature_create_note.domain.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * This response is based on prompt created
 * [Provide in JSON format including summary with financials if applicable, TODOs of actors post conversation, domain. Do not include conversation in response.
]
 */
@Parcelize
data class DetailResponse(

	@field:SerializedName("Financials")
	val financials: Financials? = null,

	@field:SerializedName("Summary")
	val summary: String? = null,

	@field:SerializedName("TODOs")
	val tODOs: List<String?>? = null,

	@field:SerializedName("Domain")
	val domain: String? = null
) : Parcelable

@Parcelize
data class Financials(

	@field:SerializedName("Property Value")
	val propertyValue: String? = null
) : Parcelable
