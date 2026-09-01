package com.maya.jobs.data.repository

import com.maya.jobs.data.api.MayaApi
import com.maya.jobs.data.dto.toDomain
import com.maya.jobs.domain.model.JobListingPage
import com.maya.jobs.domain.repository.JobRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JobRepositoryImpl(private val api: MayaApi) : JobRepository {
    override suspend fun getJobs(category: String?, keyword: String?, page: Int, limit: Int): JobListingPage =
        withContext(Dispatchers.IO) {
            api.getJobs(category, keyword, page, limit).toDomain()
        }
}
