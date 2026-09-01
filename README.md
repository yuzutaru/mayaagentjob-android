# Maya Android (Kotlin + Jetpack Compose)

Native Android client for **Maya** — job search + portfolio/CV generator.
Part of the `mayaagentjob-workspace` ecosystem. Consumes the Python FastAPI backend
(`mayaagentjob-backend-python`).

## Stack
- Kotlin 2.0 + Jetpack Compose (Material 3), Navigation Compose
- Retrofit + OkHttp + kotlinx.serialization (generated contract mirrors `openapi.json`)
- Clean Architecture: `data` / `domain` / `ui` layers

## Build
```bash
# Requires JDK 17+ and Android SDK (ANDROID_HOME set)
./gradlew assembleDebug

# Point the app at a backend (default is emulator host 10.0.2.2:8000)
./gradlew assembleDebug -PapiBaseUrl=http://10.0.2.2:8000/
```

## Structure
```
app/src/main/java/com/maya/jobs/
├── MayaApplication.kt        # DI wiring (repos from ApiClient)
├── MainActivity.kt
├── data/
│   ├── api/ApiClient.kt      # Retrofit + MayaApi endpoints
│   ├── dto/                  # kotlinx.serialization DTOs + mappers
│   └── repository/           # Api-backed repository impls
├── domain/
│   ├── model/                # Pure Kotlin entities (Portfolio, JobListing)
│   └── repository/           # Interfaces
└── ui/
    ├── navigation/           # Bottom-nav scaffold
    ├── theme/
    ├── home/  jobs/  portfolio/
```

## Status
Scaffold complete: navigation, job search list + pagination, portfolio import/
save/export, and API integration. Onboarding and LinkedIn-PDF import are next.

## Contract Sync (manual)
`app/src/main/java/com/maya/jobs/domain/model/HomePortal.kt` is the manually-synced
twin of the web `HomePortalContract.ts` + `homePortalMockData.ts` (hero, features,
workSteps, ctaBanners). Update it in lockstep with the web contract and the iOS
twin (`Maya/APIClient/Models/HomePortal.swift`).
