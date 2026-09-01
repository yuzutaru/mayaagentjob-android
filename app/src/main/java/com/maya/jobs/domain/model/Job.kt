package com.maya.jobs.domain.model

data class JobListing(
    val jobId: String,
    val title: String,
    val company: String,
    val arrangement: String,
    val location: String,
    val categoryId: String,
    val matchScore: Double,
    val aiSummaryBullets: List<String>,
)

data class JobListingPage(
    val items: List<JobListing>,
    val total: Int,
    val page: Int,
    val limit: Int,
    val pages: Int,
)
