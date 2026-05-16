package com.enrico.nit3213.di

import com.enrico.nit3213.data.repository.AuthRepositoryImpl
import com.enrico.nit3213.data.repository.DashboardRepositoryImpl
import com.enrico.nit3213.domain.repository.AuthRepository
import com.enrico.nit3213.domain.repository.DashboardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindDashboardRepository(
        dashboardRepositoryImpl: DashboardRepositoryImpl
    ): DashboardRepository
}