package com.example.newsfeed.api

import com.example.newsfeed.data.NewsArticle

data class NewsResponse (
    val results: List<NewsArticle>)