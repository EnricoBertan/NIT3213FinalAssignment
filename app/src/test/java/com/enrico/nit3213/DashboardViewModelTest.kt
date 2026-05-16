package com.enrico.nit3213

import com.enrico.nit3213.data.model.DashboardResponse
import com.enrico.nit3213.domain.usecase.GetDashboardUseCase
import com.enrico.nit3213.ui.dashboard.DashboardViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var getDashboardUseCase: GetDashboardUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getDashboardUseCase = mockk()
        viewModel = DashboardViewModel(getDashboardUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadDashboard success updates state with entities`() = runTest {
        val entities = listOf(
            mapOf("name" to "Earth", "type" to "Planet", "description" to "Our home"),
            mapOf("name" to "Mars", "type" to "Planet", "description" to "Red planet")
        )
        coEvery { getDashboardUseCase(any()) } returns DashboardResponse(entities, 2)

        viewModel.loadDashboard("planets")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.dashboardState.value
        assertTrue(state is DashboardViewModel.DashboardState.Success)
        assertEquals(2, (state as DashboardViewModel.DashboardState.Success).entities.size)
    }

    @Test
    fun `loadDashboard failure updates state with error`() = runTest {
        coEvery { getDashboardUseCase(any()) } throws Exception("Network error")

        viewModel.loadDashboard("planets")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.dashboardState.value
        assertTrue(state is DashboardViewModel.DashboardState.Error)
    }

    @Test
    fun `loadDashboard initial state is loading`() = runTest {
        val state = viewModel.dashboardState.value
        assertTrue(state is DashboardViewModel.DashboardState.Loading)
    }
}