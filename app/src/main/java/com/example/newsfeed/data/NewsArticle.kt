package com.example.newsfeed.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsArticle (
    val source: Source, val author: String, val title: String, val description: String,
    val url: String, val urlToImage: String, val publishedAt: String) : Parcelable{

    @Parcelize
    data class  Source ( val id: String, val name: String) : Parcelable
}