package com.maya.jobs

import android.app.Application
import com.maya.jobs.data.api.ApiClient
import com.maya.jobs.data.repository.JobRepositoryImpl
import com.maya.jobs.data.repository.PortfolioRepositoryImpl
import com.maya.jobs.domain.repository.JobRepository
import com.maya.jobs.domain.repository.PortfolioRepository

class MayaApplication : Application() {

    val jobRepository: JobRepository by lazy { JobRepositoryImpl(ApiClient.api) }
    val portfolioRepository: PortfolioRepository by lazy { PortfolioRepositoryImpl(ApiClient.api) }
}
