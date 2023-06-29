package com.radiusagent.assignment.domain.repository

import com.radiusagent.assignment.data.network.model.response.FacilityResponse
import com.radiusagent.assignment.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getFacilityData(): Flow<DataState<FacilityResponse>>
}