package com.enrico.nit3213.data.repository

import com.enrico.nit3213.data.model.LoginRequest
import com.enrico.nit3213.data.model.LoginResponse
import com.enrico.nit3213.data.network.ApiService
import com.enrico.nit3213.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {

    override suspend fun login(username: String, password: String): LoginResponse {
        return apiService.login(LoginRequest(username, password))
    }
}