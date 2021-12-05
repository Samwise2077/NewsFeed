package com.example.newsfeed.ui.favorite

import androidx.lifecycle.ViewModel
import com.example.newsfeed.data.NewsArticle
import com.example.newsfeed.db.ArticleDao
import com.example.newsfeed.di.ApplicationScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteNewsViewModel @Inject constructor(
    private val articleDao: ArticleDao, @ApplicationScope val applicationScope: CoroutineScope) : ViewModel(){
    lateinit var articlesList: Flow<List<NewsArticle>>
    fun onFavoriteArticlesGot() : Flow<List<NewsArticle>> {
        return  articleDao.getArticles()
    }
}