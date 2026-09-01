package com.maya.jobs.data.dto

import com.maya.jobs.domain.model.Portfolio
import com.maya.jobs.domain.model.PortfolioArticle
import com.maya.jobs.domain.model.PortfolioCertification
import com.maya.jobs.domain.model.PortfolioEducation
import com.maya.jobs.domain.model.PortfolioProject
import com.maya.jobs.domain.model.PortfolioSkill
import com.maya.jobs.domain.model.PortfolioSocialLink
import com.maya.jobs.domain.model.PortfolioStats
import com.maya.jobs.domain.model.PortfolioWorkExperience
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PortfolioSocialLinkDto(
    val platform: String = "",
    val url: String = "",
    val handle: String = "",
)

@Serializable
data class PortfolioSkillDto(
    val name: String = "",
    val category: String = "",
    val level: String = "",
)

@Serializable
data class PortfolioProjectDto(
    val name: String = "",
    val description: String = "",
    val url: String = "",
    @SerialName("source_url") val sourceUrl: String = "",
    @SerialName("tech_stack") val techStack: List<String> = emptyList(),
    val stars: Int = 0,
    val forks: Int = 0,
    val language: String = "",
    @SerialName("is_featured") val isFeatured: Boolean = false,
)

@Serializable
data class PortfolioWorkExperienceDto(
    val company: String = "",
    val role: String = "",
    @SerialName("start_date") val startDate: String = "",
    @SerialName("end_date") val endDate: String? = null,
    val location: String = "",
    val description: String = "",
    val highlights: List<String> = emptyList(),
)

@Serializable
data class PortfolioEducationDto(
    val institution: String = "",
    val degree: String = "",
    @SerialName("start_date") val startDate: String = "",
    @SerialName("end_date") val endDate: String? = null,
    val description: String = "",
)

@Serializable
data class PortfolioCertificationDto(
    val name: String = "",
    val issuer: String = "",
    val year: String = "",
    val url: String = "",
)

@Serializable
data class PortfolioArticleDto(
    val title: String = "",
    val url: String = "",
    val description: String = "",
    @SerialName("published_at") val publishedAt: String = "",
    val tags: List<String> = emptyList(),
)

@Serializable
data class PortfolioStatsDto(
    @SerialName("total_repos") val totalRepos: Int = 0,
    @SerialName("total_stars") val totalStars: Int = 0,
    @SerialName("total_forks") val totalForks: Int = 0,
    val followers: Int = 0,
    val following: Int = 0,
    @SerialName("contributions_last_year") val contributionsLastYear: Int = 0,
    @SerialName("coding_hours") val codingHours: Double = 0.0,
    val reputation: Int = 0,
    @SerialName("articles_published") val articlesPublished: Int = 0,
)

@Serializable
data class ImportRequestDto(
    val username: String,
    @SerialName("api_key") val apiKey: String? = null,
)

@Serializable
data class PortfolioProfileDto(
    @SerialName("full_name") val fullName: String = "",
    val headline: String = "",
    val bio: String = "",
    val email: String = "",
    val phone: String = "",
    val location: String = "",
    val website: String = "",
    @SerialName("avatar_url") val avatarUrl: String = "",
    val socials: List<PortfolioSocialLinkDto> = emptyList(),
    val summary: String = "",
    val skills: List<PortfolioSkillDto> = emptyList(),
    val experience: List<PortfolioWorkExperienceDto> = emptyList(),
    val education: List<PortfolioEducationDto> = emptyList(),
    val projects: List<PortfolioProjectDto> = emptyList(),
    val certifications: List<PortfolioCertificationDto> = emptyList(),
    val articles: List<PortfolioArticleDto> = emptyList(),
    val stats: PortfolioStatsDto = PortfolioStatsDto(),
    val theme: String = "aurora",
    @SerialName("accent_color") val accentColor: String = "#6366F1",
    val id: String = "",
    @SerialName("user_id") val userId: String = "",
)

fun PortfolioProfileDto.toDomain(): Portfolio = Portfolio(
    fullName = fullName,
    headline = headline,
    bio = bio,
    email = email,
    phone = phone,
    location = location,
    website = website,
    avatarUrl = avatarUrl,
    socials = socials.map { PortfolioSocialLink(it.platform, it.url, it.handle) },
    summary = summary,
    skills = skills.map { PortfolioSkill(it.name, it.category, it.level) },
    experience = experience.map {
        PortfolioWorkExperience(it.company, it.role, it.startDate, it.endDate, it.location, it.description, it.highlights)
    },
    education = education.map { PortfolioEducation(it.institution, it.degree, it.startDate, it.endDate, it.description) },
    projects = projects.map {
        PortfolioProject(it.name, it.description, it.url, it.sourceUrl, it.techStack, it.stars, it.forks, it.language, it.isFeatured)
    },
    certifications = certifications.map { PortfolioCertification(it.name, it.issuer, it.year, it.url) },
    articles = articles.map { PortfolioArticle(it.title, it.url, it.description, it.publishedAt, it.tags) },
    stats = PortfolioStats(
        totalRepos = stats.totalRepos,
        totalStars = stats.totalStars,
        totalForks = stats.totalForks,
        followers = stats.followers,
        following = stats.following,
        contributionsLastYear = stats.contributionsLastYear,
        codingHours = stats.codingHours,
        reputation = stats.reputation,
        articlesPublished = stats.articlesPublished,
    ),
    theme = theme,
    accentColor = accentColor,
    id = id,
)

fun Portfolio.toDto(): PortfolioProfileDto = PortfolioProfileDto(
    fullName = fullName,
    headline = headline,
    bio = bio,
    email = email,
    phone = phone,
    location = location,
    website = website,
    avatarUrl = avatarUrl,
    socials = socials.map { PortfolioSocialLinkDto(it.platform, it.url, it.handle) },
    summary = summary,
    skills = skills.map { PortfolioSkillDto(it.name, it.category, it.level) },
    experience = experience.map {
        PortfolioWorkExperienceDto(it.company, it.role, it.startDate, it.endDate, it.location, it.description, it.highlights)
    },
    education = education.map { PortfolioEducationDto(it.institution, it.degree, it.startDate, it.endDate, it.description) },
    projects = projects.map {
        PortfolioProjectDto(it.name, it.description, it.url, it.sourceUrl, it.techStack, it.stars, it.forks, it.language, it.isFeatured)
    },
    certifications = certifications.map { PortfolioCertificationDto(it.name, it.issuer, it.year, it.url) },
    articles = articles.map { PortfolioArticleDto(it.title, it.url, it.description, it.publishedAt, it.tags) },
    stats = PortfolioStatsDto(
        totalRepos = stats.totalRepos,
        totalStars = stats.totalStars,
        totalForks = stats.totalForks,
        followers = stats.followers,
        following = stats.following,
        contributionsLastYear = stats.contributionsLastYear,
        codingHours = stats.codingHours,
        reputation = stats.reputation,
        articlesPublished = stats.articlesPublished,
    ),
    theme = theme,
    accentColor = accentColor,
    id = id,
)
