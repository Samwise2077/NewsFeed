package com.example.newsfeed.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsfeed.api.NewsApi
import retrofit2.HttpException
import java.io.IOException

const val NEWS_STARTING_PAGE_INDEX = 1

private const val TAG = "NewsPagingResource"

class NewsPagingResource(
    private val newsApi: NewsApi,
    private val query: String
) : PagingSource<Int, NewsArticle>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsArticle> {
        val position = params.key ?: NEWS_STARTING_PAGE_INDEX
        return try {
            val response = newsApi.searchArticles(query, position, params.loadSize)
            val articles = response.results
            LoadResult.Page(
                data = articles,
                prevKey = if(position == NEWS_STARTING_PAGE_INDEX) null else position - 1,
                nextKey =  if(articles.isEmpty()) null else position + 1
            )
        }
        catch (exception: IOException){
            Log.e(TAG, "$exception")
            LoadResult.Error(exception)
        }
        catch (exception: HttpException){
            Log.e(TAG, "$exception")
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsArticle>): Int? {
        TODO("Not yet implemented")
    }
}