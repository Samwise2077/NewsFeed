package com.example.newsfeed.ui.home

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.newsfeed.data.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository) : ViewModel(){

    val searchQuery =  MutableLiveData("")

   /* val articlesFlow = combine(
       searchQuery.asFlow()
    ) {searchQuery}.flatMapLatest{
        Log.d(TAG, ":ok")
        repository.getSearchResults(searchQuery.toString())
    }

    val articles = articlesFlow.asLiveData()*/


    val articles =
        searchQuery.switchMap {Log.d(TAG, ":ok")
            if(searchQuery.value != ""){
                repository.getSearchResults(it).cachedIn(viewModelScope)
            }
            else{
                repository.getBreakingNews().cachedIn(viewModelScope)
            }
              }

    //  val articles = articlesFlow.asLiveData()

    companion object{
        const val DEFAULT_QUERY = ""
    }
}