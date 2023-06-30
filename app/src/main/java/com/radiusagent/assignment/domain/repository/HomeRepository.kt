package com.radiusagent.assignment.domain.repository

import com.radiusagent.assignment.data.cache.database.model.FacilityCache
import com.radiusagent.assignment.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getFacilityData(): Flow<DataState<FacilityCache>>
}