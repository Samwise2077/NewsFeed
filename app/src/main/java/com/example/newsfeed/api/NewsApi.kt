package com.example.newsfeed.api

import androidx.viewbinding.BuildConfig
import com.example.newsfeed.data.NewsArticle
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {

    companion object{
        const val CLIENT_ID = "356d64b4bfde4cd492ef415beabba030"
        const val BASE_URL = "https://newsapi.org/"
    }


    @GET("v2/everything")
    suspend fun searchArticles (
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String = CLIENT_ID
    ) :  NewsResponse
}