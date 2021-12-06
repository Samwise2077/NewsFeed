package com.example.newsfeed.ui.article

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.newsfeed.data.NewsArticle
import com.example.newsfeed.db.ArticleDao
import com.example.newsfeed.di.ApplicationScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor
    (private val state: SavedStateHandle,
     @ApplicationScope val applicationScope: CoroutineScope,
     private val articleDao: ArticleDao) : ViewModel() {
    val article = state.get<NewsArticle>("article")!!

    fun onFavoritesChanged(article: NewsArticle){
        applicationScope.launch {
            articleDao.insert(article)
        }
    }



}