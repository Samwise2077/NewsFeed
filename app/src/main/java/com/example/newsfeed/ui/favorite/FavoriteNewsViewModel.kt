package com.example.newsfeed.ui.favorite

import androidx.lifecycle.ViewModel
import com.example.newsfeed.data.NewsArticle
import com.example.newsfeed.db.ArticleDao
import com.example.newsfeed.di.ApplicationScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteNewsViewModel @Inject constructor(
    private val articleDao: ArticleDao, @ApplicationScope val applicationScope: CoroutineScope) : ViewModel(){
    lateinit var articlesList: Flow<List<NewsArticle>>

    private val favoriteNewsEventChannel = Channel<FavoriteNewsEvent>()
    val favoriteNewsEvent = favoriteNewsEventChannel.receiveAsFlow()
    fun onFavoriteArticlesGot() : Flow<List<NewsArticle>> {
        return  articleDao.getArticles()

    }

    fun onArticleSwiped(article: NewsArticle?) {
        applicationScope.launch {
            if (article != null) {
                articleDao.delete(article)
                favoriteNewsEventChannel.send(FavoriteNewsEvent.ShowUndoDeleteMessage(article))
            }

        }

    }
    fun onUndoClick(article: NewsArticle){
        applicationScope.launch {
            articleDao.insert(article)
        }
    }

    sealed class FavoriteNewsEvent{
        data class ShowUndoDeleteMessage(val article: NewsArticle) : FavoriteNewsEvent()
    }
}