package com.radiusagent.assignment.data.repository

import com.radiusagent.assignment.data.cache.database.dao.FacilityDao
import com.radiusagent.assignment.data.network.service.HomeService
import com.radiusagent.assignment.domain.extension.convertFacilityToFacilityCache
import com.radiusagent.assignment.domain.repository.HomeRepository
import com.radiusagent.assignment.domain.state.DataState
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService,
    private val facilityDao: FacilityDao
) : HomeRepository {
    override fun getFacilityData() = flow {
        emit(DataState.loading())
        val facilityCache = convertFacilityToFacilityCache(homeService.getFacilityInfo())
        emit(DataState.success(facilityCache))
        facilityDao.upsert(facilityCache)
    }.catch {
        emit(DataState.error("Unknown error"))
    }
}