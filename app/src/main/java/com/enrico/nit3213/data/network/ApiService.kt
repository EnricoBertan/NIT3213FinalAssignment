
package com.enrico.nit3213.data.network

import com.enrico.nit3213.data.model.DashboardResponse
import com.enrico.nit3213.data.model.LoginRequest
import com.enrico.nit3213.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("sydney/auth")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("dashboard/{keypass}")
    suspend fun getDashboard(@Path("keypass") keypass: String): DashboardResponse
}