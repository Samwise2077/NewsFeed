package com.example.newsfeed.data

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize


@Entity(tableName = "articles_table")
@Parcelize
@TypeConverters(Converters::class)
data class NewsArticle(@PrimaryKey val url: String, val title: String, val description: String, val publishedAt: String, val urlToImage: String,     @Embedded
@TypeConverters(Converters::class) val source: Source) :
    Parcelable {

    @Parcelize
    data class Source(val name: String) : Parcelable


}

