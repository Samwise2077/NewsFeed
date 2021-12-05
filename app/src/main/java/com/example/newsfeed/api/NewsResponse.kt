package com.example.newsfeed.api

import com.example.newsfeed.data.NewsArticle

data class NewsResponse(
    val status : String,
    val totalResults: Int,
    val articles: List<NewsArticle>
)

