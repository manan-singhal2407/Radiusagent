package com.radiusagent.assignment.domain.extension

import com.radiusagent.assignment.data.cache.database.model.FacilityCache
import com.radiusagent.assignment.data.network.model.response.FacilityResponse

fun convertFacilityToFacilityCache(facilityResponse: FacilityResponse): FacilityCache {
    val exclusions: MutableList<FacilityCache.Exclusion> = mutableListOf()
    facilityResponse.exclusions.forEach {
        val item: MutableList<FacilityCache.Exclusion.Option> = mutableListOf()
        it.forEach { exclusion ->
            item.add(FacilityCache.Exclusion.Option(exclusion.facilityId, exclusion.optionsId))
        }
        exclusions.add(FacilityCache.Exclusion(item))
    }
    val facilities: MutableList<FacilityCache.Facility> = mutableListOf()
    facilityResponse.facilities.forEach { facility ->
        val item: MutableList<FacilityCache.Facility.Option> = mutableListOf()
        facility.options.forEach { facilityOption ->
            item.add(
                FacilityCache.Facility.Option(
                    facilityOption.name,
                    facilityOption.icon,
                    facilityOption.id
                )
            )
        }
        facilities.add(FacilityCache.Facility(facility.facilityId, facility.name, item))
    }

    return FacilityCache(facilities = facilities, exclusions = exclusions)
}