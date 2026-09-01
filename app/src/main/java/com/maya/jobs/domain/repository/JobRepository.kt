package com.maya.jobs.domain.repository

import com.maya.jobs.domain.model.JobListingPage

interface JobRepository {
    suspend fun getJobs(
        category: String? = null,
        keyword: String? = null,
        page: Int = 1,
        limit: Int = 9,
    ): JobListingPage
}
