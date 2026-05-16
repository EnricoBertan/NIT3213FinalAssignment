package com.enrico.nit3213.domain.usecase

import com.enrico.nit3213.data.model.DashboardResponse
import com.enrico.nit3213.domain.repository.DashboardRepository
import javax.inject.Inject

class GetDashboardUseCase @Inject constructor(
    private val dashboardRepository: DashboardRepository
) {
    suspend operator fun invoke(keypass: String): DashboardResponse {
        return dashboardRepository.getDashboard(keypass)
    }
}