package com.enrico.nit3213.data.repository

import com.enrico.nit3213.data.model.DashboardResponse
import com.enrico.nit3213.data.network.ApiService
import com.enrico.nit3213.domain.repository.DashboardRepository
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : DashboardRepository {

    override suspend fun getDashboard(keypass: String): DashboardResponse {
        return apiService.getDashboard(keypass)
    }
}