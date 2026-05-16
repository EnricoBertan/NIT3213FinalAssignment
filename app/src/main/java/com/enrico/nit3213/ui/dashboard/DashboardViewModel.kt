package com.enrico.nit3213.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enrico.nit3213.domain.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository
) : ViewModel() {

    sealed class DashboardState {
        object Loading : DashboardState()
        data class Success(val entities: List<Map<String, String>>, val total: Int) : DashboardState()
        data class Error(val message: String) : DashboardState()
    }

    private val _dashboardState = MutableStateFlow<DashboardState>(DashboardState.Loading)
    val dashboardState: StateFlow<DashboardState> = _dashboardState

    fun loadDashboard(keypass: String) {
        viewModelScope.launch {
            _dashboardState.value = DashboardState.Loading
            try {
                val response = dashboardRepository.getDashboard(keypass)
                _dashboardState.value = DashboardState.Success(
                    response.entities,
                    response.entityTotal
                )
            } catch (e: Exception) {
                _dashboardState.value = DashboardState.Error("Failed to load dashboard.")
            }
        }
    }
}