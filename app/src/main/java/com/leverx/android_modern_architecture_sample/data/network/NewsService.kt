package com.leverx.android_modern_architecture_sample.data.network

import androidx.annotation.IntRange
import com.leverx.android_modern_architecture_sample.data.network.models.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    companion object {
        const val COUNT_ARTICLES = 10
    }

    @GET("everything")
    suspend fun getArticles(
        @Query("from") date: String,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(
            from = 1,
            to = COUNT_ARTICLES.toLong()
        ) pageSize: Int = COUNT_ARTICLES,
        @Query("q") newsCategory: String = "Android",
        @Query("sortBy") sortBy: String = "date",
        @Query("apiKey") apiKey: String = "6635128a70ec471ca36ebbbcf869353d" // BuildConfig.API_KEY //  // BuildConfig.API_KEY //
    ): Response<News>

    @GET("everything")
    suspend fun getSearchArticles(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(
            from = 1,
            to = COUNT_ARTICLES.toLong()
        ) pageSize: Int = COUNT_ARTICLES,
        @Query("q") newsCategory: String = "bmw",
        @Query("sortBy") sortBy: String = "popularity",
        @Query("apiKey") apiKey: String = "6635128a70ec471ca36ebbbcf869353d" // BuildConfig.API_KEY //  //BuildConfig.API_KEY
    ): Response<News>
}
