package com.radiusagent.assignment.domain.di

import com.radiusagent.assignment.data.cache.database.dao.FacilityDao
import com.radiusagent.assignment.data.network.service.HomeService
import com.radiusagent.assignment.data.repository.HomeRepositoryImpl
import com.radiusagent.assignment.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideHomeRepository(homeService: HomeService, facilityDao: FacilityDao): HomeRepository {
        return HomeRepositoryImpl(homeService = homeService, facilityDao = facilityDao)
    }
}