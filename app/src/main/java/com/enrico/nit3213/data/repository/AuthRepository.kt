package com.enrico.nit3213.domain.repository

import com.enrico.nit3213.data.model.LoginResponse

interface AuthRepository {
    suspend fun login(username: String, password: String): LoginResponse
}