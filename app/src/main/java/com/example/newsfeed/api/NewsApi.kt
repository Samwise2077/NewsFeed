package com.example.newsfeed.api

import androidx.viewbinding.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    companion object{
      //  const val CLIENT_ID = BuildConfig.
        const val BASE_URL = "https://newsapi.org/"
    }

    @GET("/everything")
    suspend fun searchArticles (
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ) : NewsResponse
}