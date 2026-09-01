package com.maya.jobs.data.repository

import com.maya.jobs.data.api.MayaApi
import com.maya.jobs.data.dto.ImportRequestDto
import com.maya.jobs.data.dto.toDomain
import com.maya.jobs.data.dto.toDto
import com.maya.jobs.domain.model.Portfolio
import com.maya.jobs.domain.repository.PortfolioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PortfolioRepositoryImpl(private val api: MayaApi) : PortfolioRepository {

    override suspend fun importFromProvider(provider: String, username: String, apiKey: String?): Portfolio =
        withContext(Dispatchers.IO) {
            api.importFromProvider(provider, ImportRequestDto(username, apiKey)).toDomain()
        }

    override suspend fun save(profile: Portfolio): Portfolio = withContext(Dispatchers.IO) {
        api.saveProfile(profile.toDto()).toDomain()
    }

    override suspend fun listByUser(userId: String): List<Portfolio> = withContext(Dispatchers.IO) {
        api.listProfiles(userId).map { it.toDomain() }
    }

    override suspend fun exportPdf(profile: Portfolio): ByteArray = withContext(Dispatchers.IO) {
        api.exportPdfBytes(profile.toDto()).bytes()
    }

    override suspend fun exportHtml(profile: Portfolio): String = withContext(Dispatchers.IO) {
        api.exportHtmlBody(profile.toDto())
    }
}
