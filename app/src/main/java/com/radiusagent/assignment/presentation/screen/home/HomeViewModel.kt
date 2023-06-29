package com.radiusagent.assignment.presentation.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radiusagent.assignment.data.cache.database.dao.FacilityDao
import com.radiusagent.assignment.data.cache.datastore.HomeSettings
import com.radiusagent.assignment.data.network.model.response.FacilityResponse
import com.radiusagent.assignment.domain.extension.convertFacilityCacheToFacility
import com.radiusagent.assignment.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val homeSettings: HomeSettings,
    private val facilityDao: FacilityDao
) : ViewModel() {
    var loading by mutableStateOf(false)
    var facilityResponse by mutableStateOf<FacilityResponse?>(null)
    var snackbarMessage by mutableStateOf("")

    init {
        fetchHomeScreen()
    }

    private fun fetchHomeScreen() {
        if (homeSettings.getHomeDataLastLoadDate().isNullOrEmpty() ||
            homeSettings.getHomeDataLastLoadDate() != getCurrentDate()
        ) {
            Timber.e("Manan: API call")
            loadFacilityDataFromServer()
        } else {
            Timber.e("Manan: Local storage call")
            loadFacilityDataFromLocal()
        }
    }

    private fun loadFacilityDataFromLocal() {
        facilityDao.getHomePageData().onEach {
            if (it.isNotEmpty()) {
                facilityResponse = convertFacilityCacheToFacility(it.last())
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
                homeSettings.saveHomeDataLastLoadDate(getCurrentDate())
            }
            dataState.error?.let {
                snackbarMessage = it
            }
        }.launchIn(viewModelScope)
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH) + 1
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        return "$year-$month-$day"
    }
}