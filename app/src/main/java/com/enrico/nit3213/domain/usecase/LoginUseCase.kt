package com.enrico.nit3213.domain.usecase

import com.enrico.nit3213.data.model.LoginResponse
import com.enrico.nit3213.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): LoginResponse {
        return authRepository.login(username, password)
    }
}