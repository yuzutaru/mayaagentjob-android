package com.maya.jobs.domain.model

/**
 * Pure Kotlin home-portal marketing contract, mirrored from the web client's
 * `src/domain/entities/HomePortalContract.ts` and its mock data
 * `src/data/mock/homePortalMockData.ts`.
 *
 * MANUAL SYNC: keep this twin in lockstep with the web contract and the iOS
 * twin (`Maya/APIClient/Models/HomePortal.swift`). See workspace `.agents/AGENTS.md`.
 */

data class HomeFeature(
    val id: String,
    val title: String,
    val description: String,
    val iconName: String,
)

data class HowWeWorkStep(
    val stepNumber: Int,
    val title: String,
    val description: String,
    val iconName: String,
    val isActive: Boolean = false,
)

data class CtaBanner(
    val id: String,
    val type: String,
    val title: String,
    val description: String,
    val buttonText: String,
    val buttonActionUrl: String,
)

data class HomePortal(
    val brandName: String,
    val brandSubtitle: String,
    val heroHeadline: String,
    val heroHighlightWord: String,
    val heroQuote: String,
    val heroQuoteHighlightWord: String,
    val features: List<HomeFeature>,
    val workSteps: List<HowWeWorkStep>,
    val ctaBanners: List<CtaBanner>,
) {
    companion object {
        val default: HomePortal = HomePortal(
            brandName = "Maya",
            brandSubtitle = "Portfolio Builder & Career Assistant",
            heroHeadline = "Build a Portfolio Website & PDF CV",
            heroHighlightWord = "PDF CV",
            heroQuote = "\"Maya turns your skills into a stunning portfolio website and a shareable PDF CV — then finds the job that matches.\"",
            heroQuoteHighlightWord = "portfolio website",
            features = listOf(
                HomeFeature(
                    id = "feature-portfolio",
                    title = "Portfolio Web Builder",
                    description = "Design a beautiful, responsive portfolio website from your real GitHub, GitLab or LinkedIn profiles.",
                    iconName = "layout",
                ),
                HomeFeature(
                    id = "feature-pdf",
                    title = "PDF & CV Export",
                    description = "Export a polished PDF CV and a shareable HTML site in one click — recruiter-ready in minutes.",
                    iconName = "file-down",
                ),
                HomeFeature(
                    id = "feature-jobs",
                    title = "AI Job Matching",
                    description = "One of Maya's features: get roles matched to your technical stack and preferences.",
                    iconName = "sparkles",
                ),
            ),
            workSteps = listOf(
                HowWeWorkStep(
                    stepNumber = 1,
                    title = "Import your profiles",
                    description = "Connect GitHub, GitLab, Bitbucket or a LinkedIn PDF export to pull your work history.",
                    iconName = "import",
                ),
                HowWeWorkStep(
                    stepNumber = 2,
                    title = "Build & customize",
                    description = "Design your portfolio website with a live preview as you edit.",
                    iconName = "layout",
                    isActive = true,
                ),
                HowWeWorkStep(
                    stepNumber = 3,
                    title = "Export PDF & site",
                    description = "Download a polished PDF CV and your portfolio website in one click.",
                    iconName = "file-down",
                ),
                HowWeWorkStep(
                    stepNumber = 4,
                    title = "Get AI matches",
                    description = "Receive jobs matched to your portfolio and preferences.",
                    iconName = "sparkles",
                ),
            ),
            ctaBanners = listOf(
                CtaBanner(
                    id = "cta-candidate",
                    type = "candidate",
                    title = "Build Your Portfolio",
                    description = "Import your GitHub, GitLab or Bitbucket profile and generate a stunning portfolio & CV in one click.",
                    buttonText = "Open Builder",
                    buttonActionUrl = "/portfolio",
                ),
                CtaBanner(
                    id = "cta-jobs",
                    type = "jobs",
                    title = "Find Your Dream Job",
                    description = "Explore jobs matched to your technical stack and apply in seconds with Maya job search.",
                    buttonText = "Find Jobs",
                    buttonActionUrl = "/jobs",
                ),
            ),
        )
    }
}
