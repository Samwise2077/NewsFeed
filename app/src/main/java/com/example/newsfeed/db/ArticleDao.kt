package com.example.newsfeed.db

import androidx.room.*
import com.example.newsfeed.data.NewsArticle
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleDao{

    @Query("SELECT * FROM articles_table")
    fun getArticles() : Flow<List<NewsArticle>>

    @Insert
    suspend fun insert(newsArticle: NewsArticle)

    @Update
    suspend fun update(newsArticle: NewsArticle)

    @Delete
    suspend fun delete(newsArticle: NewsArticle)
}