package com.maya.jobs.data.dto

import com.maya.jobs.domain.model.JobListing
import com.maya.jobs.domain.model.JobListingPage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobListingDto(
    @SerialName("job_id") val jobId: String = "",
    val title: String = "",
    val company: String = "",
    val arrangement: String = "",
    val location: String = "",
    @SerialName("category_id") val categoryId: String = "",
    @SerialName("match_score") val matchScore: Double = 0.0,
    @SerialName("ai_summary_bullets") val aiSummaryBullets: List<String> = emptyList(),
)

@Serializable
data class JobListingPageDto(
    val items: List<JobListingDto> = emptyList(),
    val total: Int = 0,
    val page: Int = 1,
    val limit: Int = 9,
    val pages: Int = 1,
)

fun JobListingPageDto.toDomain(): JobListingPage = JobListingPage(
    items = items.map {
        JobListing(it.jobId, it.title, it.company, it.arrangement, it.location, it.categoryId, it.matchScore, it.aiSummaryBullets)
    },
    total = total,
    page = page,
    limit = limit,
    pages = pages,
)
