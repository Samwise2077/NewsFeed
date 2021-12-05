package com.example.newsfeed.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsfeed.data.NewsArticle

@Database(entities = [NewsArticle::class], version = 1)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao() : ArticleDao
}