package com.example.newsfeed.api

import androidx.viewbinding.BuildConfig
import com.example.newsfeed.data.NewsArticle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {

    companion object{
        const val CLIENT_ID = "639f191fb9a840bd8dc7994cb6660cdb"
        const val BASE_URL = "https://newsapi.org/"
    }

    @Headers("X-Api-Key: ${CLIENT_ID}")
    @GET("v2/everything")
    suspend fun searchArticles (
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ) : NewsResponse

    @Headers("X-Api-Key: ${CLIENT_ID}")
    @GET("v2/top-headlines")
    suspend fun searchBreakingNews(
        @Query("country") country: String,
        @Query("page") page: Int,
       @Query("pageSize") pageSize: Int,
    ) : NewsResponse
}