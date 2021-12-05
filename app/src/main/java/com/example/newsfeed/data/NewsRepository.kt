package com.example.newsfeed.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.newsfeed.api.NewsApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "NewsRepository"

@Singleton
class NewsRepository @Inject constructor(private val newsApi: NewsApi) {
    fun getSearchResults(query: String): LiveData<PagingData<NewsArticle>> {
        Log.d(TAG, "getSearchResults: fine")
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {NewsPagingResource(newsApi, query)}
        ).liveData
    }
}