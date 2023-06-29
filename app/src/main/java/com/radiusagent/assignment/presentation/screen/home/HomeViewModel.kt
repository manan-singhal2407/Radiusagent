package com.radiusagent.assignment.presentation.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radiusagent.assignment.data.cache.database.dao.FacilityDao
import com.radiusagent.assignment.data.network.model.response.FacilityResponse
import com.radiusagent.assignment.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val facilityDao: FacilityDao
) : ViewModel() {
    var loading by mutableStateOf(false)
    var facilityResponse by mutableStateOf<FacilityResponse?>(null)
    var snackbarMessage by mutableStateOf("")

    init {
        loadHomePageInfo()
    }

    private fun loadHomePageInfo() {
        loadFacilityDataFromLocal()
    }

    private fun loadFacilityDataFromLocal() {
        Timber.e("Manan: Started")
        facilityDao.getHomePageData().onEach {
            Timber.e("Manan: " + it.size.toString())
            if (it.isNotEmpty()) {
                Timber.e("Manan: A " + it.size.toString())
//                facilityResponse = FacilityResponse(it.last().facilities, it.last().exclusions)
            } else {
                loadFacilityDataFromServer()
            }
        }.launchIn(viewModelScope)
    }

    private fun loadFacilityDataFromServer() {
        homeRepository.getFacilityData().onEach { dataState ->
            loading = dataState.loading
            dataState.data?.let {
                facilityResponse = it
            }
            dataState.error?.let {
                snackbarMessage = it
            }
        }.launchIn(viewModelScope)
    }
}