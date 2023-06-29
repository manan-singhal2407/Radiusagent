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

fun convertFacilityCacheToFacility(facilityCache: FacilityCache): FacilityResponse {
    val exclusions: MutableList<MutableList<FacilityResponse.Exclusion>> = mutableListOf()
    facilityCache.exclusions.forEach {
        val item: MutableList<FacilityResponse.Exclusion> = mutableListOf()
        it.option.forEach { option ->
            item.add(FacilityResponse.Exclusion(option.facilityId, option.optionsId))
        }
        exclusions.add(item)
    }
    val facilities: MutableList<FacilityResponse.Facility> = mutableListOf()
    facilityCache.facilities.forEach { facility ->
        val item: MutableList<FacilityResponse.Facility.Option> = mutableListOf()
        facility.option.forEach { option ->
            item.add(FacilityResponse.Facility.Option(option.name, option.icon, option.id))
        }
        facilities.add(FacilityResponse.Facility(facility.facilityId, facility.name, item))
    }

    return FacilityResponse(facilities = facilities, exclusions = exclusions)
}
