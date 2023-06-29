package com.radiusagent.assignment.data.network.service

import com.radiusagent.assignment.data.network.model.response.FacilityResponse
import retrofit2.http.GET

interface HomeService {
    @GET("iranjith4/ad-assignment/db")
    suspend fun getFacilityInfo(): FacilityResponse
}