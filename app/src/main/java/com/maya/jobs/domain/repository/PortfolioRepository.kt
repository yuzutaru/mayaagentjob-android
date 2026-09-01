package com.maya.jobs.domain.repository

import com.maya.jobs.domain.model.Portfolio

/** Contract for portfolio data access consumed by ViewModels. */
interface PortfolioRepository {
    suspend fun importFromProvider(provider: String, username: String, apiKey: String? = null): Portfolio

    suspend fun exportPdf(profile: Portfolio): ByteArray

    suspend fun exportHtml(profile: Portfolio): String

    suspend fun save(profile: Portfolio): Portfolio

    suspend fun listByUser(userId: String = "temp-user-1"): List<Portfolio>
}
