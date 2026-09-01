package com.maya.jobs.data.api

import com.maya.jobs.BuildConfig
import com.maya.jobs.data.dto.ImportRequestDto
import com.maya.jobs.data.dto.JobListingPageDto
import com.maya.jobs.data.dto.PortfolioProfileDto
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface MayaApi {

    // ── Jobs ──
    @GET("api/v1/jobs")
    suspend fun getJobs(
        @Query("category") category: String? = null,
        @Query("keyword") keyword: String? = null,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 9,
    ): JobListingPageDto

    // ── Portfolio import ──
    @POST("api/v1/portfolio/import/{provider}")
    suspend fun importFromProvider(
        @Path("provider") provider: String,
        @Body body: ImportRequestDto,
    ): PortfolioProfileDto

    // ── Portfolio persistence ──
    @POST("api/v1/portfolio/profiles")
    suspend fun saveProfile(@Body profile: PortfolioProfileDto): PortfolioProfileDto

    @GET("api/v1/portfolio/profiles")
    suspend fun listProfiles(@Query("user_id") userId: String = "temp-user-1"): List<PortfolioProfileDto>

    // ── Portfolio export ──
    @POST("api/v1/portfolio/export/pdf")
    suspend fun exportPdfBytes(@Body profile: PortfolioProfileDto): ResponseBody

    @POST("api/v1/portfolio/export/html")
    suspend fun exportHtmlBody(@Body profile: PortfolioProfileDto): String
}

object ApiClient {
    private const val BASE_URL: String = BuildConfig.API_BASE_URL

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
            }
        )
        .build()

    val api: MayaApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(MayaApi::class.java)
}
