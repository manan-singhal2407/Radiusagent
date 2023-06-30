package com.radiusagent.assignment.domain.extension

import com.radiusagent.assignment.data.cache.database.model.FacilityCache
import com.radiusagent.assignment.data.network.model.response.FacilityResponse

fun convertFacilityToFacilityCache(facilityResponse: FacilityResponse): FacilityCache {
    val disabledMap = mutableMapOf<String, MutableList<String>>()
    facilityResponse.exclusions.forEach { exclusionList ->
        val list = mutableListOf<String>()
        exclusionList.forEach { list.add(it.optionsId) }
        list.forEach { disabledMap.getOrPut(it) { mutableListOf() }.addAll(list) }
    }
    for ((key, valueList) in disabledMap) {
        valueList.remove(key)
    }

    val facilities = mutableListOf<FacilityCache.Facility>()
    facilityResponse.facilities.forEach { facility ->
        val item = mutableListOf<FacilityCache.Facility.Option>()
        facility.options.forEach { facilityOption ->
            item.add(
                FacilityCache.Facility.Option(
                    facilityOption.name,
                    facilityOption.icon,
                    facilityOption.id,
                    disabledMap[facilityOption.id] ?: emptyList(),
                )
            )
        }
        facilities.add(FacilityCache.Facility(facility.facilityId, facility.name, item))
    }

    return FacilityCache(facilities = facilities)
}