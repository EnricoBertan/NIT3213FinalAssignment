package com.enrico.nit3213.data.model

data class DashboardResponse(
    val entities: List<Map<String, Any>>,
    val entityTotal: Int
)