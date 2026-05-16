package com.enrico.nit3213.domain.repository

import com.enrico.nit3213.data.model.DashboardResponse

interface DashboardRepository {
    suspend fun getDashboard(keypass: String): DashboardResponse
}