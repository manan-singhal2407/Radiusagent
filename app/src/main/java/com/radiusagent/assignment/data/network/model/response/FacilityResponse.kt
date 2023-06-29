package com.radiusagent.assignment.data.network.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FacilityResponse(
    @Json(name = "facilities")
    val facilities: List<Facility>,
    @Json(name = "exclusions")
    val exclusions: List<List<Exclusion>>
) {
    @JsonClass(generateAdapter = true)
    data class Facility(
        @Json(name = "facility_id")
        val facilityId: String,
        @Json(name = "name")
        val name: String,
        @Json(name = "options")
        val options: List<Option>
    ) {
        @JsonClass(generateAdapter = true)
        data class Option(
            @Json(name = "name")
            val name: String,
            @Json(name = "icon")
            val icon: String,
            @Json(name = "id")
            val id: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class Exclusion(
        @Json(name = "facility_id")
        val facilityId: String,
        @Json(name = "options_id")
        val optionsId: String
    )
}