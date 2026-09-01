package com.maya.jobs.domain.model

/** Pure Kotlin domain models mirroring the backend PortfolioProfile contract. */

data class PortfolioSkill(val name: String, val category: String, val level: String = "")

data class PortfolioProject(
    val name: String,
    val description: String = "",
    val url: String = "",
    val sourceUrl: String = "",
    val techStack: List<String> = emptyList(),
    val stars: Int = 0,
    val forks: Int = 0,
    val language: String = "",
    val isFeatured: Boolean = false,
)

data class PortfolioWorkExperience(
    val company: String,
    val role: String,
    val startDate: String = "",
    val endDate: String? = null,
    val location: String = "",
    val description: String = "",
    val highlights: List<String> = emptyList(),
)

data class PortfolioEducation(
    val institution: String,
    val degree: String,
    val startDate: String = "",
    val endDate: String? = null,
    val description: String = "",
)

data class PortfolioCertification(
    val name: String,
    val issuer: String = "",
    val year: String = "",
    val url: String = "",
)

data class PortfolioArticle(
    val title: String,
    val url: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val tags: List<String> = emptyList(),
)

data class PortfolioSocialLink(
    val platform: String,
    val url: String,
    val handle: String = "",
)

data class PortfolioStats(
    val totalRepos: Int = 0,
    val totalStars: Int = 0,
    val totalForks: Int = 0,
    val followers: Int = 0,
    val following: Int = 0,
    val contributionsLastYear: Int = 0,
    val codingHours: Double = 0.0,
    val reputation: Int = 0,
    val articlesPublished: Int = 0,
)

data class Portfolio(
    val fullName: String,
    val headline: String = "",
    val bio: String = "",
    val email: String = "",
    val phone: String = "",
    val location: String = "",
    val website: String = "",
    val avatarUrl: String = "",
    val socials: List<PortfolioSocialLink> = emptyList(),
    val summary: String = "",
    val skills: List<PortfolioSkill> = emptyList(),
    val experience: List<PortfolioWorkExperience> = emptyList(),
    val education: List<PortfolioEducation> = emptyList(),
    val projects: List<PortfolioProject> = emptyList(),
    val certifications: List<PortfolioCertification> = emptyList(),
    val articles: List<PortfolioArticle> = emptyList(),
    val stats: PortfolioStats = PortfolioStats(),
    val theme: String = "aurora",
    val accentColor: String = "#6366F1",
    val id: String = "",
)
