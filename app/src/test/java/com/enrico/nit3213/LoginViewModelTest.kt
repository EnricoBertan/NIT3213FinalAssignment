package com.enrico.nit3213

import com.enrico.nit3213.data.model.LoginResponse
import com.enrico.nit3213.domain.repository.AuthRepository
import com.enrico.nit3213.ui.login.LoginViewModel
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
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var authRepository: AuthRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        authRepository = mockk()
        viewModel = LoginViewModel(authRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login with empty username shows error`() = runTest {
        viewModel.login("", "Enrico")
        val state = viewModel.loginState.value
        assertTrue(state is LoginViewModel.LoginState.Error)
    }

    @Test
    fun `login with empty password shows error`() = runTest {
        viewModel.login("s8164431", "")
        val state = viewModel.loginState.value
        assertTrue(state is LoginViewModel.LoginState.Error)
    }

    @Test
    fun `login success updates state with keypass`() = runTest {
        coEvery { authRepository.login(any(), any()) } returns LoginResponse("planets")
        viewModel.login("s8164431", "Enrico")
        testDispatcher.scheduler.advanceUntilIdle()
        val state = viewModel.loginState.value
        assertTrue(state is LoginViewModel.LoginState.Success)
        assertEquals("planets", (state as LoginViewModel.LoginState.Success).keypass)
    }

    @Test
    fun `login failure updates state with error`() = runTest {
        coEvery { authRepository.login(any(), any()) } throws Exception("Unauthorized")
        viewModel.login("s8164431", "Enrico")
        testDispatcher.scheduler.advanceUntilIdle()
        val state = viewModel.loginState.value
        assertTrue(state is LoginViewModel.LoginState.Error)
    }
}