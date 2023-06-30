package com.radiusagent.assignment.presentation.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radiusagent.assignment.data.cache.database.dao.FacilityDao
import com.radiusagent.assignment.data.cache.database.model.FacilityCache
import com.radiusagent.assignment.data.cache.datastore.HomeSettings
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
    var facilityCache by mutableStateOf<FacilityCache?>(null)
    var snackbarMessage by mutableStateOf("")
    val selectedPropertyType = mutableStateListOf<String>()

    init {
        fetchHomeScreen()
    }

    private fun fetchHomeScreen() {
        if (homeSettings.getHomeDataLastLoadDate().isNullOrEmpty() ||
            homeSettings.getHomeDataLastLoadDate() != getCurrentDate()
        ) {
            loadFacilityDataFromServer()
        } else {
            loadFacilityDataFromLocal()
        }
    }

    private fun loadFacilityDataFromLocal() {
        facilityDao.getHomePageData().onEach {
            if (it.isNotEmpty()) {
                facilityCache = it.last()
                Timber.e("Manan: " + facilityCache)
            } else {
                loadFacilityDataFromServer()
            }
        }.launchIn(viewModelScope)
    }

    private fun loadFacilityDataFromServer() {
        homeRepository.getFacilityData().onEach { dataState ->
            dataState.data?.let {
                facilityCache = it
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
        val month: Int = calendar.get(Calendar.MONTH) + CALENDAR_MONTH_INCREMENT
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        return "$year-$month-$day"
    }

    fun onChipClick(index: String, list: List<String>) {
        if (checkIfChipIsEnabled(list)) {
            if (selectedPropertyType.contains(index)) {
                selectedPropertyType.remove(index)
            } else {
                selectedPropertyType.add(index)
            }
        }
    }

    fun checkIfChipIsEnabled(list: List<String>): Boolean {
        list.forEach {
            if (selectedPropertyType.contains(it)) {
                return false
            }
        }
        return true
    }

    companion object {
        const val CALENDAR_MONTH_INCREMENT = 1
    }
}